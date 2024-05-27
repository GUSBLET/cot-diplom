const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');

const newPasswordInput = document.getElementById('newPassword');
const newConfirmationPasswordInput = document.getElementById('newConfirmationPassword');

if (token) {
    newPasswordInput.value = token;
    newConfirmationPasswordInput.value = token;
}