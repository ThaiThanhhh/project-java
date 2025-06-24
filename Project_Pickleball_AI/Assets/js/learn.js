document.addEventListener('DOMContentLoaded', function() {
    // Giả lập hàm lấy thông tin người dùng (thay bằng hệ thống xác thực thật)
    function getUserInfo() {
        return {
            userId: localStorage.getItem('userId') || 'user_' + Math.random().toString(36).substr(2, 9),
            email: localStorage.getItem('userEmail') || 'example@domain.com'
        };
    }

    // Tạo Peer ID cố định
    const userInfo = getUserInfo();
    const fixedPeerId = 'peer_' + userInfo.userId;
    const peer = new Peer(fixedPeerId, { debug: 3 }); // Debug level 3 để ghi log chi tiết hơn

    const myPeerIdSpan = document.getElementById('my-peer-id');
    const chatMessages = document.getElementById('chatMessages');
    const messageInput = document.getElementById('messageInput');
    const sendBtn = document.getElementById('sendBtn');
    const connectBtn = document.getElementById('connectBtn');
    const remotePeerId = document.getElementById('remotePeerId');
    const videoCallBtn = document.getElementById('videoCallBtn');
    const endCallBtn = document.getElementById('endCallBtn');
    const endCallOverlayBtn = document.getElementById('endCallOverlayBtn');
    const pauseVideoBtn = document.getElementById('pauseVideoBtn');
    const muteMicBtn = document.getElementById('muteMicBtn');
    const courseIdInput = document.getElementById('courseId');
    const myVideo = document.getElementById('my-video');
    const remoteVideo = document.getElementById('remote-video');
    const conversationList = document.getElementById('conversationList');
    const videoCallArea = document.getElementById('videoCallArea');
    const chatTitle = document.getElementById('chatTitle');

    let conn = null;
    let currentCall = null;
    let currentPeerId = null;
    let isVideoPaused = false;
    let isMicMuted = false;
    let myPeerId = null;

    function saveChatMessage(sender, message, peerId, courseId = "KH001") {
        const chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]');
        chatHistory.push({ sender, message, peerId, myPeerId, courseId, timestamp: new Date().toISOString() });
        localStorage.setItem('chatHistory', JSON.stringify(chatHistory));
        renderConversations();
        if (currentPeerId === peerId) {
            renderMessages(peerId, courseId);
        }
    }

    function saveCallHistory(peerId, startTime, courseId = "KH001") {
        const callHistory = JSON.parse(localStorage.getItem('callHistory') || '[]');
        const callId = Date.now().toString();
        callHistory.push({ callId, peerId, myPeerId, courseId, startTime, endTime: null });
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

    function saveConnection(peerId, courseId) {
        const connections = JSON.parse(localStorage.getItem('savedConnections') || '[]');
        if (!connections.find(c => c.myPeerId === myPeerId && c.peerId === peerId && c.courseId === courseId)) {
            connections.push({ myPeerId, peerId, courseId });
            localStorage.setItem('savedConnections', JSON.stringify(connections));
        }
    }

    function renderConversations() {
        const chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]')
            .filter(c => (c.myPeerId === myPeerId && c.courseId === courseIdInput.value) || 
                         (c.peerId === myPeerId && c.courseId === courseIdInput.value));
        const callHistory = JSON.parse(localStorage.getItem('callHistory') || '[]')
            .filter(c => (c.myPeerId === myPeerId && c.courseId === courseIdInput.value) || 
                         (c.peerId === myPeerId && c.courseId === courseIdInput.value));

        const peerIds = [...new Set([...chatHistory.map(c => c.peerId), ...callHistory.map(c => c.peerId)])];
        conversationList.innerHTML = '';

        peerIds.forEach(peerId => {
            const lastChat = chatHistory.filter(c => c.peerId === peerId || c.myPeerId === peerId).slice(-1)[0];
            const lastCall = callHistory.filter(c => c.peerId === peerId || c.myPeerId === peerId).slice(-1)[0];
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
                    <p>Course: ${lastEvent.courseId}</p>
                    <p>${lastEvent.message || `Call started at ${new Date(lastEvent.startTime).toLocaleTimeString()}`}</p>
                    <p class="timestamp">${new Date(lastEvent.timestamp || lastEvent.startTime).toLocaleString()}</p>
                `;
                item.onclick = () => {
                    currentPeerId = peerId;
                    renderMessages(peerId, lastEvent.courseId);
                    renderConversations();
                    chatTitle.textContent = `Chat with Peer: ${peerId} (Course: ${lastEvent.courseId})`;
                    messageInput.disabled = !conn;
                    sendBtn.disabled = !conn;
                };
                conversationList.appendChild(item);
            }
        });
    }

    function renderMessages(peerId, courseId) {
        const chatHistory = JSON.parse(localStorage.getItem('chatHistory') || '[]')
            .filter(c => ((c.myPeerId === myPeerId && c.peerId === peerId) || (c.myPeerId === peerId && c.peerId === myPeerId)) && c.courseId === courseId);

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

    function toggleVideoPause() {
        if (!myVideo.srcObject) return;
        const tracks = myVideo.srcObject.getVideoTracks();
        isVideoPaused = !isVideoPaused;
        tracks.forEach(track => track.enabled = !isVideoPaused);
        pauseVideoBtn.classList.toggle('active', isVideoPaused);
        pauseVideoBtn.innerHTML = `<i class="fas fa-${isVideoPaused ? 'play' : 'pause'}"></i>`;
        console.log('Video pause toggled:', isVideoPaused);
    }

    function toggleMic() {
        if (!myVideo.srcObject) return;
        const tracks = myVideo.srcObject.getAudioTracks();
        isMicMuted = !isMicMuted;
        tracks.forEach(track => track.enabled = !isMicMuted);
        muteMicBtn.classList.toggle('active', isMicMuted);
        muteMicBtn.innerHTML = `<i class="fas fa-microphone${isMicMuted ? '-slash' : ''}"></i>`;
        console.log('Mic mute toggled:', isMicMuted);
    }

    function endCall() {
        if (currentCall) {
            try {
                currentCall.close();
                console.log('Call closed successfully.');
            } catch (err) {
                console.error('Error closing call:', err);
            }
            currentCall = null;
        }
        if (myVideo.srcObject) {
            const myTracks = myVideo.srcObject.getTracks();
            myTracks.forEach(track => {
                try {
                    track.stop();
                    console.log('Stopped myVideo track:', track.kind);
                } catch (err) {
                    console.error('Error stopping myVideo track:', err);
                }
            });
            myVideo.srcObject = null;
        }
        if (remoteVideo.srcObject) {
            const remoteTracks = remoteVideo.srcObject.getTracks();
            remoteTracks.forEach(track => {
                try {
                    track.stop();
                    console.log('Stopped remoteVideo track:', track.kind);
                } catch (err) {
                    console.error('Error stopping remoteVideo track:', err);
                }
            });
            remoteVideo.srcObject = null;
        }
        videoCallArea.style.display = 'none';
        videoCallBtn.style.display = 'inline-block';
        endCallBtn.style.display = 'none';
        pauseVideoBtn.style.display = 'none';
        muteMicBtn.style.display = 'none';
        isVideoPaused = false;
        isMicMuted = false;
        pauseVideoBtn.classList.remove('active');
        muteMicBtn.classList.remove('active');
        pauseVideoBtn.innerHTML = `<i class="fas fa-pause"></i>`;
        muteMicBtn.innerHTML = `<i class="fas fa-microphone"></i>`;
        console.log('Call ended, all streams stopped, UI reset.');
    }

    peer.on('open', function(id) {
        myPeerId = id;
        myPeerIdSpan.textContent = id;
        if (!localStorage.getItem('userId')) {
            localStorage.setItem('userId', userInfo.userId);
        }
        renderConversations();
        console.log('PeerJS connected with ID:', id);
    });

    peer.on('error', function(err) {
        console.error('PeerJS error:', err.type, err.message);
        let errorMessage = 'An error occurred. Please try again.';
        switch (err.type) {
            case 'peer-unavailable':
                errorMessage = 'The specified Peer ID is unavailable or not connected.';
                break;
            case 'id-taken':
                errorMessage = 'This Peer ID is already in use. Please log in with a different account.';
                break;
            case 'browser-incompatible':
                errorMessage = 'Your browser is not compatible with WebRTC.';
                break;
            case 'disconnected':
                errorMessage = 'Disconnected from PeerJS server. Please check your internet connection.';
                break;
        }
        alert(errorMessage);
    });

    connectBtn.onclick = function() {
        const peerId = remotePeerId.value.trim();
        const courseId = courseIdInput.value.trim() || 'KH001';
        if (!peerId) {
            alert('Please enter a Peer ID.');
            return;
        }
        if (!courseId) {
            alert('Please enter a Course ID.');
            return;
        }
        if (conn && !conn.closed) {
            alert('You are already connected to a peer. Disconnect first.');
            return;
        }
        console.log('Attempting to connect to peer:', peerId);
        conn = peer.connect(peerId);
        conn.on('open', function() {
            currentPeerId = peerId;
            saveConnection(peerId, courseId);
            renderMessages(peerId, courseId);
            chatTitle.textContent = `Chat with Peer: ${peerId} (Course: ${courseId})`;
            messageInput.disabled = false;
            sendBtn.disabled = false;
            remotePeerId.value = '';
            courseIdInput.value = courseId;
            renderConversations();
            console.log('Connected to peer:', peerId, 'Course:', courseId);
        });
        conn.on('data', function(data) {
            saveChatMessage('Friend', data, peerId, courseId);
        });
        conn.on('close', function() {
            console.log('Connection closed with peer:', peerId);
            conn = null;
            messageInput.disabled = true;
            sendBtn.disabled = true;
            alert('Connection to peer has been closed.');
        });
        conn.on('error', function(err) {
            console.error('Connection error:', err);
            alert('Failed to connect to peer: ' + err.message);
        });
    };

    peer.on('connection', function(connection) {
        if (conn && !conn.closed) {
            connection.close();
            return;
        }
        conn = connection;
        currentPeerId = connection.peer;
        const courseId = courseIdInput.value.trim() || 'KH001';
        conn.on('data', function(data) {
            saveChatMessage('Friend', data, currentPeerId, courseId);
        });
        conn.on('close', function() {
            console.log('Incoming connection closed with peer:', currentPeerId);
            conn = null;
            messageInput.disabled = true;
            sendBtn.disabled = true;
            alert('Connection to peer has been closed.');
        });
        renderMessages(currentPeerId, courseId);
        chatTitle.textContent = `Chat with Peer: ${currentPeerId} (Course: ${courseId})`;
        messageInput.disabled = false;
        sendBtn.disabled = false;
        renderConversations();
        console.log('Incoming connection from peer:', currentPeerId);
    });

    sendBtn.onclick = function() {
        const msg = messageInput.value.trim();
        const courseId = courseIdInput.value.trim() || 'KH001';
        if (conn && !conn.closed && msg) {
            conn.send(msg);
            saveChatMessage('You', msg, currentPeerId, courseId);
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
        if (!conn || conn.closed) {
            alert('Connection to peer is not active. Please reconnect.');
            return;
        }
        const courseId = courseIdInput.value.trim() || 'KH001';
        try {
            console.log('Requesting media access for video call...');
            const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
            myVideo.srcObject = stream;
            myVideo.play().catch(err => console.error('Error playing local video:', err));
            console.log('Local stream acquired, initiating call to:', currentPeerId);
            currentCall = peer.call(currentPeerId, stream);
            const callId = saveCallHistory(currentPeerId, new Date().toISOString(), courseId);
            currentCall.on('stream', function(remoteStream) {
                remoteVideo.srcObject = remoteStream;
                remoteVideo.play().catch(err => console.error('Error playing remote video:', err));
                videoCallArea.style.display = 'flex';
                pauseVideoBtn.style.display = 'inline-block';
                muteMicBtn.style.display = 'inline-block';
                console.log('Remote stream received, video call area displayed.');
            });
            currentCall.on('error', function(err) {
                console.error('Call error:', err);
                alert('Video call failed: ' + err.message);
                endCall();
            });
            currentCall.on('close', function() {
                console.log('Call closed by remote peer.');
                updateCallEndTime(callId);
                endCall();
            });
            videoCallArea.style.display = 'flex';
            videoCallBtn.style.display = 'none';
            endCallBtn.style.display = 'inline-block';
            pauseVideoBtn.style.display = 'inline-block';
            muteMicBtn.style.display = 'inline-block';
            console.log('Video call initiated, video call area displayed.');
        } catch (err) {
            console.error('Error accessing media devices:', err.name, err.message);
            alert('Failed to access camera or microphone: ' + err.message);
            endCall();
        }
    };

    peer.on('call', async function(call) {
        if (currentCall) {
            call.close();
            return;
        }
        const courseId = courseIdInput.value.trim() || 'KH001';
        try {
            console.log('Receiving incoming call from:', call.peer);
            const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
            myVideo.srcObject = stream;
            myVideo.play().catch(err => console.error('Error playing local video:', err));
            call.answer(stream);
            const callId = saveCallHistory(call.peer, new Date().toISOString(), courseId);
            call.on('stream', function(remoteStream) {
                remoteVideo.srcObject = remoteStream;
                remoteVideo.play().catch(err => console.error('Error playing remote video:', err));
                videoCallArea.style.display = 'flex';
                pauseVideoBtn.style.display = 'inline-block';
                muteMicBtn.style.display = 'inline-block';
                console.log('Incoming call stream received, video call area displayed.');
            });
            call.on('error', function(err) {
                console.error('Call error:', err);
                alert('Video call failed: ' + err.message);
                endCall();
            });
            call.on('close', function() {
                console.log('Incoming call closed.');
                updateCallEndTime(callId);
                endCall();
            });
            videoCallArea.style.display = 'flex';
            videoCallBtn.style.display = 'none';
            endCallBtn.style.display = 'inline-block';
            pauseVideoBtn.style.display = 'inline-block';
            muteMicBtn.style.display = 'inline-block';
            currentCall = call;
            console.log('Incoming call accepted, video call area displayed.');
        } catch (err) {
            console.error('Error accessing media devices:', err.name, err.message);
            alert('Failed to access camera or microphone: ' + err.message);
            call.close();
        }
    });

    pauseVideoBtn.onclick = toggleVideoPause;
    muteMicBtn.onclick = toggleMic;
    endCallBtn.onclick = endCall;
    endCallOverlayBtn.onclick = endCall;
});