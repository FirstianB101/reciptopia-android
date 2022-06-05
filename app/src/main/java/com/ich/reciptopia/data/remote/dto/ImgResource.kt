package com.ich.reciptopia.data.remote.dto

data class ImgResource(
    val description: String,
    val `file`: String,
    val filename: String,
    val `open`: Boolean,
    val readable: Boolean,
    val uri: String,
    val url: String
)