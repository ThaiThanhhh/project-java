# backend/app.py
from flask import Flask, request, jsonify
import os, openai

openai.api_key = os.getenv("OPENAI_API_KEY")
app = Flask(__name__)

# dữ liệu mẫu
PRODUCTS = {
    "pb1": {
        "name": "Vợt Pickleball Pro X",
        "description": "Vợt cao cấp, lõi graphite, bề mặt textured giúp kiểm soát spin tối ưu.",
        "specs": "240g, 16x8 in, cân bằng trung tâm."
    }
}

@app.route('/api/ask', methods=['POST'])
def ask():
    data = request.json
    q   = data.get('question')
    pid = data.get('productId', 'pb1')
    p   = PRODUCTS.get(pid, PRODUCTS['pb1'])
    
    system = f"""
Bạn là trợ lý bán hàng cho sản phẩm pickleball:
Tên: {p['name']}
Mô tả: {p['description']}
Thông số kỹ thuật: {p['specs']}
Trả lời ngắn gọn, chi tiết về lợi ích và cách sử dụng.
"""
    resp = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=[
            {"role":"system",  "content": system},
            {"role":"user",    "content": q}
        ],
        temperature=0.7
    )
    ans = resp.choices[0].message.content
    return jsonify(answer=ans)

if __name__ == '__main__':
    app.run(port=5000, debug=True)
