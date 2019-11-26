package com.thebrodyaga.imageslist.screen.image.fullscreen


import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.thebrodyaga.imageslist.R
import com.thebrodyaga.imageslist.di.Injector
import com.thebrodyaga.imageslist.repository.ImageRepository
import com.thebrodyaga.imageslist.screen.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_image_fullscreen.*
import kotlinx.android.synthetic.main.item_image.view.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ImageFullscreenFragment : BaseFragment() {

    @Inject
    lateinit var imageRepository: ImageRepository

    private val imageId: String
        get() = arguments?.getString(EXTRA) ?: throw IllegalArgumentException("need put imageId")

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusImageComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.fragment_image_fullscreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(image, imageId)

        unSubscribeOnDestroy(
            imageRepository.getImageById(imageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Glide.with(view.context)
                        .load(it.download_url)
                        .into(image)
                }, { it.printStackTrace() })
        )
    }

    companion object {

        private const val EXTRA = "EXTRA"
        fun newInstance(id: String): ImageFullscreenFragment =
            ImageFullscreenFragment().apply {
                arguments = Bundle().also { it.putString(EXTRA, id) }
            }
    }
}
