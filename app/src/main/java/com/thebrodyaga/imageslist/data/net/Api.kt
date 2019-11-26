package com.thebrodyaga.imageslist.data.net

import com.thebrodyaga.imageslist.domain.entity.net.ImageNet
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("v2/list")
    fun loadImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 30
    ): Single<List<ImageNet>>

    @GET("id/{id}/info")
    fun loadById(@Path("id") id: String): Single<ImageNet>
}