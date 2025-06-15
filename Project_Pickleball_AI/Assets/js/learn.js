document.addEventListener('DOMContentLoaded', function() {
            const peer = new Peer();
            const myPeerIdSpan = document.getElementById('my-peer-id');
            const chatMessages = document.getElementById('chatMessages');
            const messageInput = document.getElementById('messageInput');
            const sendBtn = document.getElementById('sendBtn');
            const connectBtn = document.getElementById('connectBtn');
            const remotePeerId = document.getElementById('remotePeerId');
            const videoCallBtn = document.getElementById('videoCallBtn');
            const endCallBtn = document.getElementById('endCallBtn');
            const endCallOverlayBtn = document.getElementById('endCallOverlayBtn');
            const myVideo = document.getElementById('my-video');
            const remoteVideo = document.getElementById('remote-video');
            const conversationList = document.getElementById('conversationList');
            const videoCallArea = document.getElementById('videoCallArea');
            const chatTitle = document.getElementById('chatTitle');

            let conn = null;
            let currentCall = null;
            let currentPeerId = null;

            function saveChatMessage(sender, message, peerId, courseId = "KH001") {
                const chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]');
                chatHistory.push({ sender, message, peerId, timestamp: new Date().toISOString(), courseId });
                localStorage.setItem('chatHistory', JSON.stringify(chatHistory));
                renderConversations();
                if (currentPeerId === peerId) {
                    renderMessages(peerId);
                }
            }

            function saveCallHistory(peerId, startTime, courseId = "KH001") {
                const callHistory = JSON.parse(localStorage.getItem('callHistory') || '[]');
                const callId = Date.now().toString();
                callHistory.push({ callId, peerId, startTime, endTime: null, courseId });
                localStorage.setItem('callHistory', JSON.stringify(callHistory));
                renderConversations();
                return callId;
            }

            function updateCallEndTime(callId) {
                const callHistory = JSON.parse(localStorage.getItem('callHistory') || '[]');
                const call = callHistory.find(c => c.callId === callId);
                if (call) {
                    call.endTime = new Date().toISOString();
                    localStorage.setItem('callHistory', JSON.stringify(callHistory));
                    renderConversations();
                }
            }

            function renderConversations() {
                const chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]');
                const callHistory = JSON.parse(localStorage.getItem('callHistory') || '[]');

                const peerIds = [...new Set([...chatHistory.map(c => c.peerId), ...callHistory.map(c => c.peerId)])];
                conversationList.innerHTML = '';

                peerIds.forEach(peerId => {
                    const lastChat = chatHistory.filter(c => c.peerId === peerId).slice(-1)[0];
                    const lastCall = callHistory.filter(c => c.peerId === peerId).slice(-1)[0];
                    const lastEvent = lastChat && lastCall 
                        ? (new Date(lastChat.timestamp) > new Date(lastCall.startTime) ? lastChat : lastCall)
                        : lastChat || lastCall;

                    if (lastEvent) {
                        const item = document.createElement('div');
                        item.classList.add('conversation-item');
                        if (peerId === currentPeerId) {
                            item.classList.add('active');
                        }
                        item.innerHTML = `
                            <p><strong>Peer: ${peerId}</strong></p>
                            <p>${lastEvent.message || `Call started at ${new Date(lastEvent.startTime).toLocaleTimeString()}`}</p>
                            <p class="timestamp">${new Date(lastEvent.timestamp || lastEvent.startTime).toLocaleString()}</p>
                        `;
                        item.onclick = () => {
                            currentPeerId = peerId;
                            renderMessages(peerId);
                            renderConversations();
                            chatTitle.textContent = `Chat with Peer: ${peerId}`;
                            messageInput.disabled = !conn;
                            sendBtn.disabled = !conn;
                        };
                        conversationList.appendChild(item);
                    }
                });
            }

            function renderMessages(peerId) {
                const chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]')
                    .filter(c => c.peerId === peerId);

                chatMessages.innerHTML = '';
                chatHistory.forEach(c => {
                    const message = document.createElement('div');
                    message.classList.add('message', c.sender === 'You' ? 'sent' : 'received');
                    message.innerHTML = `
                        <div class="message-content">${c.message}</div>
                        <div class="timestamp">${new Date(c.timestamp).toLocaleTimeString()}</div>
                    `;
                    chatMessages.appendChild(message);
                });
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }

            function endCall() {
                if (currentCall) {
                    currentCall.close();
                    videoCallArea.style.display = 'none';
                    myVideo.srcObject = null;
                    remoteVideo.srcObject = null;
                    videoCallBtn.style.display = 'inline-block';
                    endCallBtn.style.display = 'none';
                    console.log('Call ended by user.');
                }
            }

            peer.on('open', function(id) {
                myPeerIdSpan.textContent = id;
                renderConversations();
                console.log('PeerJS connected with ID:', id);
            });

            connectBtn.onclick = function() {
                const peerId = remotePeerId.value.trim();
                if (!peerId) {
                    alert('Please enter a Peer ID.');
                    return;
                }
                conn = peer.connect(peerId);
                conn.on('open', function() {
                    currentPeerId = peerId;
                    renderMessages(peerId);
                    chatTitle.textContent = `Chat with Peer: ${peerId}`;
                    messageInput.disabled = false;
                    sendBtn.disabled = false;
                    remotePeerId.value = '';
                    renderConversations();
                    console.log('Connected to peer:', peerId);
                });
                conn.on('data', function(data) {
                    saveChatMessage('Friend', data, peerId);
                });
                conn.on('error', function(err) {
                    console.error('Connection error:', err);
                    alert('Failed to connect to peer.');
                });
            };

            peer.on('connection', function(connection) {
                conn = connection;
                currentPeerId = connection.peer;
                conn.on('data', function(data) {
                    saveChatMessage('Friend', data, currentPeerId);
                });
                renderMessages(currentPeerId);
                chatTitle.textContent = `Chat with Peer: ${currentPeerId}`;
                messageInput.disabled = false;
                sendBtn.disabled = false;
                renderConversations();
                console.log('Incoming connection from peer:', currentPeerId);
            });

            sendBtn.onclick = function() {
                const msg = messageInput.value.trim();
                if (conn && msg) {
                    conn.send(msg);
                    saveChatMessage('You', msg, currentPeerId);
                    messageInput.value = '';
                }
            };

            messageInput.onkeypress = function(e) {
                if (e.key === 'Enter' && !e.shiftKey) {
                    e.preventDefault();
                    sendBtn.click();
                }
            };

            videoCallBtn.onclick = async function() {
                if (!currentPeerId) {
                    alert('Please connect to a peer first.');
                    return;
                }
                try {
                    const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
                    myVideo.srcObject = stream;
                    currentCall = peer.call(currentPeerId, stream);
                    const callId = saveCallHistory(currentPeerId, new Date().toISOString());
                    currentCall.on('stream', function(remoteStream) {
                        remoteVideo.srcObject = remoteStream;
                        videoCallArea.style.display = 'flex';
                        console.log('Remote stream received, video call area displayed.');
                    });
                    currentCall.on('error', function(err) {
                        console.error('Call error:', err);
                        alert('Video call failed.');
                        videoCallArea.style.display = 'none';
                    });
                    currentCall.on('close', function() {
                        updateCallEndTime(callId);
                        videoCallArea.style.display = 'none';
                        myVideo.srcObject = null;
                        remoteVideo.srcObject = null;
                        videoCallBtn.style.display = 'inline-block';
                        endCallBtn.style.display = 'none';
                        console.log('Call closed.');
                    });
                    videoCallArea.style.display = 'flex';
                    videoCallBtn.style.display = 'none';
                    endCallBtn.style.display = 'inline-block';
                    console.log('Video call initiated, video call area displayed.');
                } catch (err) {
                    console.error('Error accessing media devices:', err);
                    alert('Failed to access camera or microphone. Please check permissions.');
                }
            };

            peer.on('call', async function(call) {
                try {
                    const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
                    myVideo.srcObject = stream;
                    call.answer(stream);
                    const callId = saveCallHistory(call.peer, new Date().toISOString());
                    call.on('stream', function(remoteStream) {
                        remoteVideo.srcObject = remoteStream;
                        videoCallArea.style.display = 'flex';
                        console.log('Incoming call stream received, video call area displayed.');
                    });
                    call.on('error', function(err) {
                        console.error('Call error:', err);
                        alert('Video call failed.');
                        videoCallArea.style.display = 'none';
                    });
                    call.on('close', function() {
                        updateCallEndTime(callId);
                        videoCallArea.style.display = 'none';
                        myVideo.srcObject = null;
                        remoteVideo.srcObject = null;
                        videoCallBtn.style.display = 'inline-block';
                        endCallBtn.style.display = 'none';
                        console.log('Incoming call closed.');
                    });
                    videoCallArea.style.display = 'flex';
                    videoCallBtn.style.display = 'none';
                    endCallBtn.style.display = 'inline-block';
                    currentCall = call;
                    console.log('Incoming call accepted, video call area displayed.');
                } catch (err) {
                    console.error('Error accessing media devices:', err);
                    alert('Failed to access camera or microphone. Please check permissions.');
                }
            });

            endCallBtn.onclick = endCall;
            endCallOverlayBtn.onclick = endCall;
        });