package com.thebrodyaga.imageslist.di.modules

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

class GlideModule : AppGlideModule() {

    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val diskCacheSizeBytes: Long = 1024 * 1024 * 20 // 20 MB
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes))
    }
}