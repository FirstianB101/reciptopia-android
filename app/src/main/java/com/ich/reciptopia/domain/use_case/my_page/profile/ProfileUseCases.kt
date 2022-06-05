package com.ich.reciptopia.domain.use_case.my_page.profile

data class ProfileUseCases(
    val editNickname: EditNicknameUseCase,
    val uploadProfileImg: UploadProfileImgUseCase,
    val getAccountProfileImg: GetAccountProfileImgUseCase
)
