package com.thebrodyaga.imageslist.domain.entity.net

data class ImageNet (
    val id: String,
    val author: String,
    val download_url: String,
    val height: Int,
    val url: String,
    val width: Int
)