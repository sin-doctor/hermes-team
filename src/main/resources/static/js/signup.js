// signup.js

$(document).ready(function () {
    $('#checkBtn').click(function () {
        const user_id = $('#user_id').val();
        if (!user_id) {
            alert('유저 아이디를 입력하세요');
            return;
        }

        $.ajax({
            url: '/check-user_id',
            type: 'POST',
            data: { user_id: user_id },
            success: function (response) {
                if (response.isDuplicate) {
                    $('#checkResult').text('이미 사용중인 아이디입니다.').css('color', 'red');
                } else {
                    $('#checkResult').text('사용가능한 아이디입니다.').css('color', 'green');
                }
            },
            error: function () {
                alert('서버 요청 중 오류가 발생했습니다. 유저네임 중복확인 불가합니다.');
            }
        });
    });

    document.getElementById("signupForm").addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 제출 동작 막기

        const password = document.getElementById("user_pw").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        const message = document.getElementById("message");

        if (password === confirmPassword) {
            message.textContent = "비밀번호가 일치합니다.";
            message.className = "success";
            // 비밀번호가 일치하면 폼 제출
            this.submit();
        } else {
            message.textContent = "비밀번호가 일치하지 않습니다. 다시 입력해주세요.";
            message.className = "error";
            // 비밀번호 불일치 시 제출되지 않음
        }
    });
});
