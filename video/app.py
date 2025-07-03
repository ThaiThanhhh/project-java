from flask import Flask, render_template, request, send_from_directory
import os
from pose_utils import process_video

app = Flask(__name__)
UPLOAD_FOLDER = 'uploads'
RESULT_FOLDER = 'results'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
os.makedirs(RESULT_FOLDER, exist_ok=True)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/upload', methods=['POST'])
def upload():
    video = request.files['video']
    path = os.path.join(UPLOAD_FOLDER, video.filename)
    video.save(path)

    result_path = os.path.join(RESULT_FOLDER, f"labeled_{video.filename}")
    process_video(path, result_path)

    return f'<h3>Video đã xử lý xong!</h3><a href="/results/{os.path.basename(result_path)}">Tải video</a>'

@app.route('/results/<filename>')
def results(filename):
    return send_from_directory(RESULT_FOLDER, filename)

if __name__ == '__main__':
    app.run(debug=True)
