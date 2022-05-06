package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Account

data class AccountDto(
    var email: String? = null,
    var nickname: String? = null,
    var profilePictureUrl: String? = null
){
    var id: Long? = null
    var role: String? = null
}

fun AccountDto.toAccount(): Account{
    return Account(
        email = email,
        nickname = nickname,
        profilePictureUrl = profilePictureUrl
    ).also {
        it.id = id
        it.role = role
    }
}