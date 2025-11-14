const wrapperBox = document.getElementById('wrapper');
const inputFieldGroup = document.getElementsByClassName('inputGroup');
const allInputs = document.querySelector('input');
const userNickname = document.getElementById('nickname');
const userEmail = document.getElementById('email');
const userPassword = document.getElementById('userPassword');
const confirmPassword = document.getElementById('confirmPassword');
const userPhone = document.getElementById('phone');
const registrationForm = document.getElementById('registrationForm');

// helperText 업데이트
// 해당 input이 있는 부모 div -> 그 div에 있는 helperText
const updateHelperText = (input, message, isValid) => {
    const inputGroup = input.parentElement;
    const helperText = inputGroup.getElementsByClassName('helperText')[0];

    if (isValid == true) {
        inputGroup.classList.remove('invalid');
        inputGroup.classList.add('valid');
        helperText.style.visibility = 'hidden';
    }
    if (isValid == false) {
        inputGroup.classList.remove('valid');
        inputGroup.classList.add('invalid');
        helperText.style.visibility = 'visible';
        helperText.innerText = message;
    }
};

// 입력이 비어 있지 않은지 확인
// 강의에선 checkEmptyInput이라고 했는데, checkNotEmptyInput이라는 이름이 더 적절하다고 생각합니다.
// (아래 함수에서 true를 반환하는 것이 비어 있지 않음을 의미하기 때문)
const checkNotEmptyInput = (input) => {
    // 공백 제거한 입력 값이 비어 있다면
    if (input.value.trim() === '') {
        updateHelperText(input, '값을 입력해주세요.', false);
        return false;
    } else {
        updateHelperText(input, '', true);
        return true;
    }
};

// 이메일 형식 검증
const validateEmailFormat = (input) => {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
    if (emailPattern.test(input.value.trim()) == true) {
        updateHelperText(input, '', true);
        return true;
    } else {
        updateHelperText(input, '유효한 이메일 주소를 입력해주세요.', false);
        return false;
    }
};

// 비밀번호 강도 확인
const checkPasswordStrength = (password) => {
    const strongPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    if (strongPattern.test(password.value) == true) {
        updateHelperText(password, '비밀번호 강도: 강함', true);
        return true;
    } else {
        updateHelperText(password, '비밀번호는 8자 이상이어야 하며, 대문자, 소문자, 특수문자를 포함하여야 합니다.', false);
        return false;
    }
};

// 비밀번호와 비밀번호 확인 입력이 같은지 검증
const validatePasswordMatch = (passwordInput, confirmInput) => {
    if (passwordInput.value !== confirmInput.value) {
        updateHelperText(confirmInput, '비밀번호가 일치하지 않습니다.', false);
        return false;
    } else {
        updateHelperText(confirmInput, '', true);
        return true;
    }
};

// 전화번호 형식 검증
const validatePhoneNumber = (input) => {
    const phonePattern = /^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
    if (phonePattern.test(input.value.trim()) == true) {
        updateHelperText(input, '', true);
        return true;
    } else {
        updateHelperText(input, '유효한 전화번호를 입력해주세요. (예: 010-1234-1234)', false);
        return false;
    }
};

// 폼 제출시 입력 필드가 유효한지 확인
const validateForm = () => {
    const isNicknameValid = checkNotEmptyInput(userNickname);
    const isEmailValid = validateEmailFormat(userEmail);
    const isPasswordStrong = checkPasswordStrength(userPassword);
    const isPasswordMatch = validatePasswordMatch(userPassword, confirmPassword);
    const isPhoneValid = validatePhoneNumber(userPhone);

    // 위의 검사를 모두 통과해야 함
    return isNicknameValid && isEmailValid && isPasswordStrong && isPasswordMatch && isPhoneValid;
};

registrationForm.addEventListener('submit', (e) => {
    // e: submit시에 발생하는 이벤트
    e.preventDefault(); // 유효성 검사를 하기 위해 기본 동작(폼 제출 동작(새로고침 포함))을 막음

    if (validateForm() == true) {
        console.log('유효성 검사 통과');
    } else {
        console.log('유효성 검사 실패');
    }
});

// 필드에 값을 입력할 때 그 필드의 테두리 색 변경, 알림 표시
document.querySelectorAll("input").forEach(input => {
    input.addEventListener('input', () => {
        switch (input.id) {
            case 'nickname':
                checkNotEmptyInput(input);
                break;
            case 'email':
                validateEmailFormat(input);
                break;
            case 'userPassword':
                checkPasswordStrength(input);
                break;
            case 'confirmPassword':
                validatePasswordMatch(userPassword, confirmPassword);
                break;
            case 'phone':
                validatePhoneNumber(input);
                break;
        }
    })
});