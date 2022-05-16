package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Account

data class AccountDto(
    var id: Long? = null,
    var role: String? = null,
    var email: String? = null,
    var nickname: String? = null,
    var profilePictureUrl: String? = null
)

fun AccountDto.toAccount(): Account{
    return Account(
        id = id,
        role = role,
        email = email,
        nickname = nickname,
        profilePictureUrl = profilePictureUrl
    )
}