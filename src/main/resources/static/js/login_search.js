document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("login_search");

    form.addEventListener("submit", (event) => {
        // 폼 제출 시점에 값들을 가져옴
        const username = document.getElementById("user_name").value.trim();
        const question = document.getElementById("question").value;
        const answer = document.getElementById("user_verification_answer").value.trim();

        // 사용자 이름 검증 (2-30자 사이, 영문/한글 허용)
        const usernameRegex = /^[a-zA-Z가-힣]{2,30}$/;

        // 답변 검증 (공백이 아닌 텍스트만)
        const answerRegex = /^[^\s]+$/;

        // 사용자 이름 검증
        if (!usernameRegex.test(username)) {
            alert("사용자 이름은 2-30자의 영문, 한글만 가능합니다.");
            document.getElementById("user_name").focus();
            event.preventDefault();
            return false;
        }

        // 보안 질문 선택 검증
        if (!question) {
            alert("보안 질문을 선택해주세요.");
            document.getElementById("question").focus();
            event.preventDefault();
            return false;
        }

        // 답변 검증
        if (!answerRegex.test(answer)) {
            alert("답변은 공백없이 입력해주세요.");
            document.getElementById("user_verification_answer").focus();
            event.preventDefault();
            return false;
        }

        // 서버로 전송하기 전에 확인 메시지 표시
        if (!confirm("입력하신 정보로 계정을 찾으시겠습니까?")) {
            event.preventDefault();
            return false;
        }

        return true; // 모든 검증 통과시 폼 제출 진행
    });
});