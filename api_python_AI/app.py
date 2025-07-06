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
logging.basicConfig(level=logging.INFO)
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
        return duration <= MAX_VIDEO_DURATION
    return False

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/analyze', methods=['POST'])
def analyze():
    if 'video' not in request.files:
        logger.error('No video file uploaded')
        return jsonify({"error": "Please select a video to analyze"}), 400
    
    video_file = request.files['video']
    
    # Check if file exists
    if video_file.filename == '':
        logger.error('Empty video file')
        return jsonify({"error": "No video file selected"}), 400
    
    # Check file format
    if not allowed_file(video_file.filename):
        logger.error(f'Invalid file format: {video_file.filename}')
        return jsonify({
            "error": "Unsupported video format",
            "supported_formats": list(ALLOWED_EXTENSIONS)
        }), 400
    
    # Check file size
    if video_file.content_length > MAX_VIDEO_SIZE:
        logger.error(f'File too large: {video_file.content_length} bytes')
        return jsonify({
            "error": "Video file too large",
            "max_size": f"{MAX_VIDEO_SIZE // (1024*1024)}MB"
        }), 400
    
    try:
        # Create secure temp file
        temp_dir = tempfile.mkdtemp()
        temp_path = os.path.join(temp_dir, secure_filename(video_file.filename))
        video_file.save(temp_path)
        logger.info(f'Temporary video saved at: {temp_path}')
        
        # Check video duration
        if not check_video_duration(temp_path):
            logger.error(f'Video too long (more than {MAX_VIDEO_DURATION} seconds)')
            return jsonify({
                "error": f"Video too long (max {MAX_VIDEO_DURATION} seconds)"
            }), 400
        
        # Check if video is readable
        cap = cv2.VideoCapture(temp_path)
        if not cap.isOpened():
            logger.error('Could not open video for reading')
            return jsonify({"error": "Could not read video. File may be corrupted."}), 400
        cap.release()
        
        # Analyze video
        logger.info('Starting video analysis...')
        analysis_result = analyze_video(temp_path)
        logger.info('Video analysis completed')
        
        if 'error' in analysis_result:
            logger.error(f'Analysis error: {analysis_result["error"]}')
            return jsonify({"error": analysis_result["error"]}), 400
        
        # Convert images to base64
        for frame in analysis_result['incorrect_frames']:
            try:
                _, buffer = cv2.imencode('.jpg', frame['image'])
                frame['image'] = f"data:image/jpeg;base64,{base64.b64encode(buffer).decode('utf-8')}"
            except Exception as e:
                logger.error(f'Error processing frame: {str(e)}')
                continue
        
        # Add video info to result
        analysis_result['video_info'] = {
            "filename": video_file.filename,
            "size": f"{os.path.getsize(temp_path) / (1024*1024):.2f}MB",
            "duration": analysis_result.get('video_info', {}).get('duration', 0)
        }
        
        return jsonify(analysis_result)
        
    except Exception as e:
        logger.error(f'Critical error processing video: {str(e)}', exc_info=True)
        return jsonify({
            "error": "An error occurred while processing the video",
            "details": str(e)
        }), 500
        
    finally:
        # Clean up temp files
        try:
            if 'temp_path' in locals() and os.path.exists(temp_path):
                os.remove(temp_path)
                logger.info(f'Deleted temp file: {temp_path}')
            if 'temp_dir' in locals() and os.path.exists(temp_dir):
                os.rmdir(temp_dir)
                logger.info(f'Deleted temp directory: {temp_dir}')
        except Exception as e:
            logger.error(f'Error cleaning up temp files: {str(e)}')

@app.route('/static/<path:filename>')
def static_files(filename):
    return send_from_directory(app.static_folder, filename)

if __name__ == '__main__':
    os.makedirs('static', exist_ok=True)
    app.run(debug=True, port=5000)