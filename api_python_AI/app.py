from flask import Flask, render_template, request, jsonify, send_from_directory
import cv2
import numpy as np
import os
import base64
from pose_utils import analyze_video
import tempfile
import logging
from werkzeug.utils import secure_filename

# Configure logging
logging.basicConfig(level=logging.DEBUG)  # Đặt mức log là DEBUG để ghi chi tiết
logger = logging.getLogger(__name__)

app = Flask(__name__, template_folder='templates', static_folder='static')

# Configuration
ALLOWED_EXTENSIONS = {'mp4', 'mov', 'avi', 'mkv'}
MAX_VIDEO_SIZE = 50 * 1024 * 1024  # 50MB
MAX_VIDEO_DURATION = 30  # 30 seconds

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def check_video_duration(video_path):
    cap = cv2.VideoCapture(video_path)
    fps = cap.get(cv2.CAP_PROP_FPS)
    frame_count = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    cap.release()
    
    if fps > 0:
        duration = frame_count / fps
        logger.debug(f"Thời lượng video: {duration} giây, FPS: {fps}, Số khung hình: {frame_count}")
        return duration <= MAX_VIDEO_DURATION
    logger.error("Không thể xác định FPS của video")
    return False

@app.route('/')
def home():
    logger.info("Truy cập trang chủ")
    return render_template('index.html')

@app.route('/analyze', methods=['POST'])
def analyze():
    logger.info("Nhận được yêu cầu phân tích video")
    if 'video' not in request.files:
        logger.error('Không có file video trong request')
        return jsonify({"error": "Please select a video to analyze"}), 400
    
    video_file = request.files['video']
    
    # Check if file exists
    if video_file.filename == '':
        logger.error('File video rỗng')
        return jsonify({"error": "No video file selected"}), 400
    
    # Check file format
    if not allowed_file(video_file.filename):
        logger.error(f'Định dạng file không được hỗ trợ: {video_file.filename}')
        return jsonify({
            "error": "Unsupported video format",
            "supported_formats": list(ALLOWED_EXTENSIONS)
        }), 400
    
    # Check file size
    if video_file.content_length and video_file.content_length > MAX_VIDEO_SIZE:
        logger.error(f'File quá lớn: {video_file.content_length} bytes')
        return jsonify({
            "error": "Video file too large",
            "max_size": f"{MAX_VIDEO_SIZE // (1024*1024)}MB"
        }), 400
    
    try:
        # Create secure temp file
        temp_dir = tempfile.mkdtemp()
        temp_path = os.path.join(temp_dir, secure_filename(video_file.filename))
        video_file.save(temp_path)
        logger.info(f"Lưu video tạm thời tại: {temp_path}")
        
        # Check video duration
        if not check_video_duration(temp_path):
            logger.error(f'Video quá dài (hơn {MAX_VIDEO_DURATION} giây)')
            return jsonify({
                "error": f"Video too long (max {MAX_VIDEO_DURATION} seconds)"
            }), 400
        
        # Check if video is readable
        cap = cv2.VideoCapture(temp_path)
        if not cap.isOpened():
            logger.error('Không thể mở video để đọc')
            return jsonify({"error": "Could not read video. File may be corrupted."}), 400
        cap.release()
        
        # Analyze video
        logger.info('Bắt đầu phân tích video...')
        analysis_result = analyze_video(temp_path)
        logger.info('Hoàn tất phân tích video')
        
        if 'error' in analysis_result:
            logger.error(f'Lỗi phân tích: {analysis_result["error"]}')
            return jsonify({"error": analysis_result["error"]}), 400
        
        # Convert images to base64
        for frame in analysis_result['incorrect_frames']:
            try:
                _, buffer = cv2.imencode('.jpg', frame['image'])
                frame['image'] = f"data:image/jpeg;base64,{base64.b64encode(buffer).decode('utf-8')}"
                logger.debug("Đã chuyển đổi khung hình sang base64")
            except Exception as e:
                logger.error(f'Lỗi khi chuyển đổi khung hình sang base64: {str(e)}')
                continue
        
        # Add video info to result
        analysis_result['video_info'] = {
            "filename": video_file.filename,
            "size": f"{os.path.getsize(temp_path) / (1024*1024):.2f}MB",
            "duration": analysis_result.get('video_info', {}).get('duration', 0)
        }
        
        logger.info("Trả về kết quả phân tích")
        return jsonify(analysis_result)
        
    except Exception as e:
        logger.error(f'Lỗi nghiêm trọng khi xử lý video: {str(e)}', exc_info=True)
        return jsonify({
            "error": "An error occurred while processing the video",
            "details": str(e)
        }), 500
        
    finally:
        # Clean up temp files
        try:
            if 'temp_path' in locals() and os.path.exists(temp_path):
                os.remove(temp_path)
                logger.info(f'Đã xóa file tạm: {temp_path}')
            if 'temp_dir' in locals() and os.path.exists(temp_dir):
                os.rmdir(temp_dir)
                logger.info(f'Đã xóa thư mục tạm: {temp_dir}')
        except Exception as e:
            logger.error(f'Lỗi khi dọn dẹp file tạm: {str(e)}')

@app.route('/static/<path:filename>')
def static_files(filename):
    logger.info(f"Phục vụ file tĩnh: {filename}")
    return send_from_directory(app.static_folder, filename)

if __name__ == '__main__':
    os.makedirs('static', exist_ok=True)
    app.run(debug=True, host='0.0.0.0', port=5000)  # Thay đổi host để cho phép kết nối từ ngoài