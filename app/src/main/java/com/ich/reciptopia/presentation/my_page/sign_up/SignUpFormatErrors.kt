package com.ich.reciptopia.presentation.my_page.sign_up

sealed class SignUpFormatErrors(val msg: String){
    object EmailBlank: SignUpFormatErrors("이메일을 입력해주세요")
    object EmailFormatError: SignUpFormatErrors("이메일 형식을 확인해주세요")
    object PasswordBlank: SignUpFormatErrors("비밀번호를 입력해주세요")
    object PasswordTooShort: SignUpFormatErrors("비밀번호가 너무 짧습니다 (8~15)")
    object PasswordTooLong: SignUpFormatErrors("비밀번호가 너무 깁니다 (8~15)")
    object PasswordFormatError: SignUpFormatErrors("비밀번호에 사용할 수 없는 문자가 존재합니다")
    object PasswordCheckError: SignUpFormatErrors("비밀번호를 확인해주세요")
    object NicknameBlank: SignUpFormatErrors("닉네임을 입력해주세요")
    object NicknameLengthError: SignUpFormatErrors("닉네임은 5~16자로 입력해주세요")
    object NoError: SignUpFormatErrors("")
}
