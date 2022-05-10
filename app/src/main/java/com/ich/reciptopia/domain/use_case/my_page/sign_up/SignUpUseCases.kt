package com.ich.reciptopia.domain.use_case.my_page.sign_up

data class SignUpUseCases(
    val emailExists: EmailExistsUseCase,
    val createAccount: CreateAccountUseCase
)
