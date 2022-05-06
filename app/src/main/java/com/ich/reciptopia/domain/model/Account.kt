package com.ich.reciptopia.domain.model

data class Account(
    var email: String? = null,
    var nickname: String? = null,
    var profilePictureUrl: String? = null
){
    var id: Long? = null
    var role: String? = null
}