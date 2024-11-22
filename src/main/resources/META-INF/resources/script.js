const chatWindow = document.getElementById('chatWindow');
const chatInput = document.getElementById('chatInput');
const sendButton = document.getElementById('sendButton');
const textTitle = document.getElementById('textTitle');
const textContent = document.getElementById('textContent');

// Function to send and receive messages using REST API
async function sendMessage() {
  const userMessage = chatInput.value.trim();
  if (!userMessage) {
    alert('Please enter a message.');
    return;
  }

  // Add user's message to the chat window
  appendMessage('You', userMessage);

  try {
    const response = await fetch('/chat', {
      method: 'POST',
      body: JSON.stringify({ message: userMessage }),
      headers: { 'Content-Type': 'application/json' }
    });
    const data = await response.json();

    // Add server's response to the chat window
    appendMessage('Server', `${data.message || 'Your message was received!'}`);
  } catch (error) {
    appendMessage('Error', 'Failed to connect to the server.');
  }

  // Clear the input
  chatInput.value = '';
}

// Function to append messages to the chat window
function appendMessage(sender, message) {
  const messageDiv = document.createElement('div');
  messageDiv.innerHTML = `<strong>${sender}:</strong> ${message}`;
  chatWindow.appendChild(messageDiv);
  chatWindow.scrollTop = chatWindow.scrollHeight; // Auto-scroll
}

// Attach event listener to the Send button
sendButton.addEventListener('click', sendMessage);

// Optional: Send message when Enter key is pressed
chatInput.addEventListener('keypress', (e) => {
  if (e.key === 'Enter') {
    e.preventDefault();
    sendMessage();
  }
});

// Toggle the visibility of the text content
textTitle.addEventListener('click', () => {
  if (textContent.classList.contains('hidden')) {
    textContent.classList.remove('hidden');
    textTitle.textContent = 'Hide Text';
  } else {
    textContent.classList.add('hidden');
    textTitle.textContent = 'Expandable Text';
  }
});

function copyText(button) {
  // Find the text to copy (previous sibling of the button)
  const textToCopy = button.previousElementSibling.textContent;

  // Copy the text to clipboard
  navigator.clipboard.writeText(textToCopy).then(() => {
    showCopyMessage();
  }).catch(err => {
    console.error('Failed to copy text: ', err);
  });
}

