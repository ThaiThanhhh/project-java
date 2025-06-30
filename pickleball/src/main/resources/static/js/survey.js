        // Dữ liệu ngôn ngữ (updated for question 6)
// const translations = {
//     vi: {
//         title: "Đánh Giá Kỹ Năng Pickleball",
//         questions: [
//             "1. Bạn đã chơi Pickleball được bao lâu?",
//             "2. Bạn chơi Pickleball bao nhiêu lần mỗi tuần (trung bình)?",
//             "3. Bạn biết và áp dụng các luật cơ bản của Pickleball như thế nào?",
//             "4. Bạn đánh giá khả năng giao bóng của mình thế nào?",
//             "5. Bạn giao tiếp và phối hợp với đối tác trong đánh đôi như thế nào?",
//             "6. Phong cách học tập ưa thích của bạn là gì?",
//             "7. Bạn đánh giá trình độ hiện tại của bạn ở mức nào?"
//         ],
//         options: [
//             ["Chưa bao giờ chơi", "Dưới 3 tháng", "3 đến 12 tháng", "Hơn 1 năm"],
//             ["Ít hơn 1 lần", "1-2 lần mỗi tuần", "3-4 lần mỗi tuần", "Hầu như mỗi ngày"],
//             ["Không biết luật", "Biết một vài luật", "Biết và áp dụng luật chính xác", "Hiểu rõ luật và có thể giải thích cho người khác"],
//             ["Khó khăn khi giao bóng", "Có thể giao bóng nhưng không ổn định", "Giao bóng ổn định với một số chiến thuật", "Có thể thực hiện nhiều kiểu giao bóng hiệu quả"],
//             ["Chưa bao giờ chơi đôi", "Đã chơi đôi nhưng giao tiếp kém", "Giao tiếp và phối hợp khá tốt", "Giao tiếp và hỗ trợ nhau với chiến thuật rõ ràng"],
//             ["Thực hành thực tế & Phân tích video", "Chỉ phân tích video", "Huấn luyện một kèm một", "Học theo nhóm"],
//             ["Người mới", "Đã biết chơi", "Chuyên nghiệp"]
//         ],
//         submit: "Gửi Đánh Giá",
//         errors: {
//             incomplete: "Vui lòng trả lời tất cả các câu hỏi (Câu %s chưa được trả lời)"
//         },
//         results: {
//             thank: "Cảm ơn bạn đã tham gia đánh giá!",
//             experience: "Thời gian chơi",
//             frequency: "Tần suất chơi",
//             rules: "Hiểu biết luật",
//             serve: "Khả năng giao bóng",
//             doubles: "Phối hợp đánh đôi",
//             movement: "Phong cách học tập",
//             tournaments: "Trình độ hiện tại",
//             summary: "Kết quả đánh giá của bạn:"
//         },
//         role: {
//             question: "Vui lòng xác nhận vai trò của bạn:",
//             student: "Tôi là Học viên",
//             coach: "Tôi là Huấn luyện viên",
//             confirmed: "Bạn đã xác nhận %role%. Kết quả đã được ghi nhận!"
//         }
//     },
//     en: {
//         title: "Pickleball Skill Assessment",
//         questions: [
//             "1. How long have you been playing Pickleball?",
//             "2. How often do you play Pickleball per week (on average)?",
//             "3. How well do you know and follow the basic rules of Pickleball?",
//             "4. How would you rate your serving ability?",
//             "5. How well do you communicate and coordinate with your partner in doubles matches?",
//             "6. What is your preferred learning style?",
//             "7. How would you rate your current skill level?"
//         ],
//         options: [
//             ["Never played", "Less than 3 months", "3 to 12 months", "More than 1 year"],
//             ["Less than once", "1–2 times a week", "3–4 times a week", "Almost every day"],
//             ["Don't know the rules", "Know a few rules", "Know and apply rules correctly", "Know the rules well and can explain them to others"],
//             ["Struggle with serving", "Can serve but not consistently", "Serve consistently with some strategy", "Can perform different types of serves effectively"],
//             ["Never played doubles", "Played doubles but communication is weak", "Communicate and coordinate fairly well", "Communicate and support each other with clear strategy"],
//             ["Practical practice & Video analysis", "Video analysis only", "One-on-one coaching", "Group learning"],
//             ["Beginner", "Intermediate", "Professional"]
//         ],
//         submit: "Submit Assessment",
//         errors: {
//             incomplete: "Please answer all questions (Question %s is not answered)"
//         },
//         results: {
//             thank: "Thank you for completing the assessment!",
//             experience: "Playing experience",
//             frequency: "Playing frequency",
//             rules: "Rules knowledge",
//             serve: "Serving ability",
//             doubles: "Doubles communication",
//             movement: "Learning style",
//             tournaments: "Current skill level",
//             summary: "Your assessment results:"
//         },
//         role: {
//             question: "Please confirm your role:",
//             student: "I am a Student",
//             coach: "I am a Coach",
//             confirmed: "You have confirmed as %role%. Results have been recorded!"
//         }
//     }
// };

// Biến lưu trữ kết quả
let surveyResults = {};
let currentLang = 'vi';

// Chức năng chuyển đổi ngôn ngữ
function changeLanguage(lang) {
    currentLang = lang;
    document.getElementById('surveyTitle').textContent = translations[lang].title;
    
    // Cập nhật câu hỏi
    const questionTitles = document.querySelectorAll('.question-title');
    questionTitles.forEach((title, index) => {
        title.textContent = translations[lang].questions[index];
    });
    
    // Cập nhật lựa chọn
    const optionTexts = document.querySelectorAll('.options span');
    let optionIndex = 0;
    translations[lang].options.forEach(optionGroup => {
        optionGroup.forEach(option => {
            optionTexts[optionIndex].textContent = option;
            optionIndex++;
        });
    });
    
    // Cập nhật nút submit
    document.getElementById('submitBtn').textContent = translations[lang].submit;
    
    // Cập nhật class active cho nút ngôn ngữ
    document.getElementById('langVi').classList.toggle('active', lang === 'vi');
    document.getElementById('langEn').classList.toggle('active', lang === 'en');
    
    // Cập nhật phần xác nhận vai trò nếu đang hiển thị
    if (document.getElementById('roleConfirmation').style.display === 'block') {
        document.getElementById('roleQuestion').textContent = translations[lang].role.question;
        document.getElementById('studentBtn').textContent = translations[lang].role.student;
        document.getElementById('coachBtn').textContent = translations[lang].role.coach;
    }
    
    // Cập nhật kết quả nếu đang hiển thị
    if (document.getElementById('result').style.display === 'block' && Object.keys(surveyResults).length > 0) {
        updateResults();
    }
}

// Hàm cập nhật kết quả
function updateResults() {
    const resultDiv = document.getElementById('result');
    const role = surveyResults.role;
    
    let resultHTML = `
        <h3>${translations[currentLang].results.thank}</h3>
        <p>${translations[currentLang].role.confirmed.replace('%role%', 
            role === 'student' ? translations[currentLang].role.student : translations[currentLang].role.coach)}</p>
        <p><strong>${translations[currentLang].results.summary}</strong></p>
        <p><strong>${translations[currentLang].results.experience}:</strong> ${getAnswerText('experience', surveyResults.experience)}</p>
        <p><strong>${translations[currentLang].results.frequency}:</strong> ${getAnswerText('frequency', surveyResults.frequency)}</p>
        <p><strong>${translations[currentLang].results.rules}:</strong> ${getAnswerText('rules', surveyResults.rules)}</p>
        <p><strong>${translations[currentLang].results.serve}:</strong> ${getAnswerText('serve', surveyResults.serve)}</p>
        <p><strong>${translations[currentLang].results.doubles}:</strong> ${getAnswerText('doubles', surveyResults.doubles)}</p>
        <p><strong>${translations[currentLang].results.movement}:</strong> ${getAnswerText('movement', surveyResults.movement)}</p>
        <p><strong>${translations[currentLang].results.tournaments}:</strong> ${getAnswerText('tournaments', surveyResults.tournaments)}</p>
        <p><strong>${translations[currentLang].role.question.split(':')[0]}:</strong> ${role === 'student' ? translations[currentLang].role.student : translations[currentLang].role.coach}</p>
    `;
    
    resultDiv.innerHTML = resultHTML;
}

// Xử lý sự kiện gửi form
document.getElementById('pickleballSurvey').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const formData = new FormData(this);
    let allAnswered = true;
    
    // Kiểm tra tất cả câu hỏi đã được trả lời
    const questionNames = ['experience', 'frequency', 'rules', 'serve', 'doubles', 'movement', 'tournaments'];
    questionNames.forEach((name, index) => {
        if (!formData.get(name)) {
            allAnswered = false;
            const errorMsg = translations[currentLang].errors.incomplete.replace('%s', index + 1);
            document.getElementById('error').textContent = errorMsg;
        }
    });
    
    if (!allAnswered) return;
    
    document.getElementById('error').textContent = '';
    
    // Thu thập kết quả
    surveyResults = {};
    questionNames.forEach(name => {
        surveyResults[name] = formData.get(name);
    });
    
    // Ẩn form và hiển thị phần xác nhận vai trò
    document.getElementById('pickleballSurvey').style.display = 'none';
    document.getElementById('roleConfirmation').style.display = 'block';
    document.getElementById('roleQuestion').textContent = translations[currentLang].role.question;
    document.getElementById('studentBtn').textContent = translations[currentLang].role.student;
    document.getElementById('coachBtn').textContent = translations[currentLang].role.coach;
    
    // Cuộn đến phần xác nhận vai trò
    document.getElementById('roleConfirmation').scrollIntoView({ behavior: 'smooth' });
});

// Xử lý sự kiện xác nhận vai trò
document.getElementById('studentBtn').addEventListener('click', function() {
    confirmRole('student');
});

document.getElementById('coachBtn').addEventListener('click', function() {
    confirmRole('coach');
});

function confirmRole(role) {
    // Lưu vai trò vào kết quả
    surveyResults.role = role;
    
    // Hiển thị kết quả hoàn chỉnh
    const resultDiv = document.getElementById('result');
    resultDiv.style.display = 'block';
    
    updateResults();
    
    // Ẩn phần xác nhận vai trò
    document.getElementById('roleConfirmation').style.display = 'none';
    
    // Cuộn đến kết quả
    resultDiv.scrollIntoView({ behavior: 'smooth' });
    
    // In kết quả ra console (có thể thay bằng gửi đến server)
    console.log("Survey results:", surveyResults);
}

// Hàm lấy văn bản tương ứng với câu trả lời
function getAnswerText(questionName, value) {
    const questionIndex = {
        experience: 0,
        frequency: 1,
        rules: 2,
        serve: 3,
        doubles: 4,
        movement: 5,
        tournaments: 6
    }[questionName];
    
    const valueIndex = {
        never: 0, beginner: 1, intermediate: 2, advanced: 3,
        rarely: 0, sometimes: 1, often: 2, daily: 3,
        dont_know: 0, few_rules: 1, apply_correctly: 2, explain_rules: 3,
        struggle: 0, inconsistent: 1, consistent: 2, effective: 3,
        never: 0, weak: 1, fair: 2, strong: 3,
        practice_video: 0, video_only: 1, one_on_one: 2, group_learning: 3,
        never: 0, local: 1, several: 2, official: 3
    }[value];
    
    return translations[currentLang].options[questionIndex][valueIndex];
}

// Khởi tạo
document.getElementById('langVi').addEventListener('click', function() {
    window.location.search = '?lang=vi';
});
document.getElementById('langEn').addEventListener('click', function() {
    window.location.search = '?lang=en';
});