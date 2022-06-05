package com.ich.reciptopia.data.remote.dto


data class AccountProfileImgDto(
    val resource: ImgResource
)

fun AccountProfileImgDto.getImageUri(): String{
    return resource.uri
}