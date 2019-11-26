package com.thebrodyaga.imageslist.repository

import com.thebrodyaga.imageslist.domain.entity.net.ImageNet
import io.reactivex.Single

interface ImageRepository {
    fun loadImages(page: Int): Single<List<ImageNet>>
    fun getImageById(id: String): Single<ImageNet>
}