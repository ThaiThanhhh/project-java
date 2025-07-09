// Simple message sending functionality
        document.addEventListener('DOMContentLoaded', function() {
            const inputField = document.querySelector('input[type="text"]');
            const sendButton = document.querySelector('.fa-paper-plane').parentNode;
            const chatContainer = document.querySelector('.chat-container');
            
            function sendMessage() {
                const messageText = inputField.value.trim();
                if (messageText) {
                    // Add user message
                    const userMessage = document.createElement('div');
                    userMessage.className = 'message-animation flex justify-end space-x-3';
                    userMessage.innerHTML = `
                        <div class="bg-blue-50 rounded-lg p-3 max-w-3xl">
                            <p class="text-gray-800">${messageText}</p>
                        </div>
                        <div class="flex-shrink-0">
                            <div class="bg-blue-100 p-2 rounded-full">
                                <i class="fas fa-user text-blue-600"></i>
                            </div>
                        </div>
                    `;
                    chatContainer.appendChild(userMessage);
                    
                    // Clear input
                    inputField.value = '';
                    
                    // Scroll to bottom
                    chatContainer.scrollTop = chatContainer.scrollHeight;
                    
                    // Simulate AI response after a delay
                    setTimeout(() => {
                        const aiMessage = document.createElement('div');
                        aiMessage.className = 'message-animation flex space-x-3';
                        aiMessage.innerHTML = `
                            <div class="flex-shrink-0">
                                <div class="bg-purple-100 p-2 rounded-full">
                                    <i class="fas fa-robot text-purple-600"></i>
                                </div>
                            </div>
                            <div class="bg-purple-50 rounded-lg p-3 max-w-3xl">
                                <p class="text-gray-800">I'm analyzing your request about "${messageText}". Here's what I found...</p>
                                <div class="mt-2 text-xs text-purple-700">
                                    <i class="fas fa-circle-notch fa-spin"></i> Generating detailed analysis...
                                </div>
                            </div>
                        `;
                        chatContainer.appendChild(aiMessage);
                        chatContainer.scrollTop = chatContainer.scrollHeight;
                    }, 1000);
                }
            }
            
            // Send message on button click
            sendButton.addEventListener('click', sendMessage);
            
            // Send message on Enter key
            inputField.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    sendMessage();
                }
            });
            
            // Example buttons functionality
            document.querySelectorAll('.bg-purple-100.text-purple-700').forEach(button => {
                button.addEventListener('click', function() {
                    inputField.value = this.textContent.trim();
                    sendMessage();
                });
            });
        });