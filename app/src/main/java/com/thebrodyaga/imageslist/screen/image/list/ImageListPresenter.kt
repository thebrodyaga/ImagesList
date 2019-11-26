package com.thebrodyaga.imageslist.screen.image.list

import com.thebrodyaga.imageslist.domain.entity.net.ImageNet
import com.thebrodyaga.imageslist.repository.ImageRepository
import com.thebrodyaga.imageslist.screen.base.BasePresenter
import com.thebrodyaga.imageslist.screen.base.Paginator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ImageListPresenter @Inject constructor(
    private val imageRepository: ImageRepository,
    private val paginator: Paginator.Store<ImageNet>
) : BasePresenter<ImageListView>() {
    private var pageDisposable: Disposable? = null

    init {
        paginator.render = { viewState.renderPaginatorState(it) }
        unSubscribeOnDestroy(paginator.sideEffects
            .subscribe { effect ->
                when (effect) {
                    is Paginator.SideEffect.LoadPage -> loadNewPage(effect.currentPage)
                    is Paginator.SideEffect.ErrorEvent -> {
                        viewState.showMessage(effect.error)
                    }
                }
            })
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        paginator.proceed(Paginator.Action.Refresh)
    }


    private fun loadNewPage(page: Int) {
        pageDisposable?.dispose()
        pageDisposable =
            imageRepository.loadImages(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { paginator.proceed(Paginator.Action.NewPage(page, it)) },
                    { e -> paginator.proceed(Paginator.Action.PageError(e)) }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        pageDisposable?.dispose()
    }

    fun loadNextLabelsPage() = paginator.proceed(Paginator.Action.LoadMore)

    fun retryLoad() = paginator.proceed(Paginator.Action.Refresh)
}