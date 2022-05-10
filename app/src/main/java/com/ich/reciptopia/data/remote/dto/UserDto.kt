package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.User

data class UserDto(
    var token: String? = null,
    var account: AccountDto? = null
)

fun UserDto.toUser(): User{
    return User(
        token = token,
        account = account?.toAccount()
    )
}