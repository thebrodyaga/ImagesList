package com.thebrodyaga.imageslist.di.image

import com.thebrodyaga.imageslist.data.net.Api
import com.thebrodyaga.imageslist.di.FirstScope
import com.thebrodyaga.imageslist.repository.ImageRepository
import com.thebrodyaga.imageslist.repository.impl.ImageRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ImageModule {

    @FirstScope
    @Provides
    fun privodeRepositpry(api: Api): ImageRepository = ImageRepositoryImpl(api)
}