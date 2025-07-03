// backend/app.js
import express from 'express';
import fetch from 'node-fetch';
import dotenv from 'dotenv';
dotenv.config();

const app = express();
app.use(express.json());

const OPENAI_KEY = process.env.OPENAI_API_KEY;
const MODEL = "gpt-3.5-turbo";

app.post('/api/ask', async (req, res) => {
  const { question, productId } = req.body;
  // ví dụ data sản phẩm pickleball
  const products = {
    "pb1": {
      name: "Vợt Pickleball Pro X",
      description: "Vợt cao cấp, lõi graphite, bề mặt textured giúp kiểm soát spin tối ưu.",
      specs: "Trọng lượng 240g, kích thước 16x8 in, mức cân bằng trung tâm."
    },
    // có thể thêm nhiều product khác
  };
  const product = products[productId] || products["pb1"];
  
  const systemPrompt = `
Bạn là trợ lý bán hàng cho sản phẩm pickleball:
Tên: ${product.name}
Mô tả: ${product.description}
Thông số kỹ thuật: ${product.specs}
Khi khách hàng hỏi, hãy trả lời ngắn gọn, thân thiện, tập trung vào thông tin kỹ thuật và lợi ích.
`;
  
  const messages = [
    { role: "system", content: systemPrompt },
    { role: "user",  content: question }
  ];
  
  const resp = await fetch("https://api.openai.com/v1/chat/completions", {
    method: "POST",
    headers: {
      "Authorization": `Bearer ${OPENAI_KEY}`,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      model: MODEL,
      messages,
      temperature: 0.7
    })
  });
  
  const data = await resp.json();
  const answer = data.choices?.[0]?.message?.content
               || "Xin lỗi, mình không thể trả lời câu hỏi này.";
  
  res.json({ answer });
});

app.listen(3000, () => console.log("Server chạy tại http://localhost:3000"));
