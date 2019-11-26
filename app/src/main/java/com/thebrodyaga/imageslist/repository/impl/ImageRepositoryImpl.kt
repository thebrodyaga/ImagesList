package com.thebrodyaga.imageslist.repository.impl

import com.thebrodyaga.imageslist.data.net.Api
import com.thebrodyaga.imageslist.domain.entity.net.ImageNet
import com.thebrodyaga.imageslist.repository.ImageRepository
import io.reactivex.Single

class ImageRepositoryImpl constructor(
    private val api: Api
) : ImageRepository {
    private val imageCash = mutableMapOf<String, ImageNet>()

    override fun getImageById(id: String): Single<ImageNet> =
        imageCash[id]?.let { Single.just(it) }
            ?: api.loadById(id)
                .doOnSuccess { imageCash[it.id] = it }

    override fun loadImages(page: Int) = api.loadImages(page)
        .doOnSuccess { imageCash.putAll(it.map { imageNet -> imageNet.id to imageNet }.toMap()) }
}