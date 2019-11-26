package com.thebrodyaga.imageslist.screen.image.list


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.Fade
import com.thebrodyaga.imageslist.R
import com.thebrodyaga.imageslist.di.Injector
import com.thebrodyaga.imageslist.domain.entity.ui.ProgressItem
import com.thebrodyaga.imageslist.navigation.Screens
import com.thebrodyaga.imageslist.navigation.TransitionBox
import com.thebrodyaga.imageslist.screen.adapters.ImageAdapter
import com.thebrodyaga.imageslist.screen.base.BaseFragment
import com.thebrodyaga.imageslist.screen.base.Paginator
import com.thebrodyaga.imageslist.screen.base.ZeroViewHelper
import com.thebrodyaga.imageslist.screen.isInvisible
import kotlinx.android.synthetic.main.fragment_image_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject

class ImageListFragment : BaseFragment(), ImageListView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ImageListPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val adapter = ImageAdapter({ item, sharedElements ->
        anyRouter().navigateToWithTransition(
            Screens.ImagesFlowScreen.ImageFullscreenScreen(item.id),
            TransitionBox(
                ChangeImageTransform(),
                null,
                null,
                ChangeImageTransform()
            ),
            *sharedElements
        )
    }, { presenter.loadNextLabelsPage() })
    private var zeroViewHelper: ZeroViewHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusImageComponent().inject(this)
        super.onCreate(savedInstanceState)
        ChangeBounds()
    }

    override fun getLayoutId(): Int = R.layout.fragment_image_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        zeroViewHelper =
            ZeroViewHelper(zero_view, { presenter.retryLoad() }, { presenter.retryLoad() })

        list.apply {

            layoutManager = StaggeredGridLayoutManager(
                calculateNoOfColumns(context, R.dimen.card_image_width),
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = this@ImageListFragment.adapter
        }

        list.setOnApplyWindowInsetsListener { v, insets ->
            v.setPadding(
                v.paddingLeft, insets.systemWindowInsetTop,
                v.paddingRight, insets.systemWindowInsetBottom
            )
            insets
        }
    }

    override fun showMessage(error: Throwable) {
        Timber.e(error)
        Toast.makeText(context, R.string.error_title, Toast.LENGTH_SHORT).show()
    }

    override fun renderPaginatorState(state: Paginator.State) {
        when (state) {
            is Paginator.State.Empty -> {
                list.isInvisible(true)
                zeroViewHelper?.showEmpty()
            }
            is Paginator.State.EmptyProgress -> {
                list.isInvisible(true)
                zeroViewHelper?.showProgress()
            }
            is Paginator.State.EmptyError -> {
                list.isInvisible(true)
                zeroViewHelper?.showError()
                Timber.e(state.error)
            }
            is Paginator.State.Data<*> -> onNewData(state.data)
            is Paginator.State.Refresh<*> -> onNewData(state.data)
            is Paginator.State.NewPageProgress<*> ->
                onNewData(state.data.toMutableList()
                    .also { it.add(ProgressItem) })
            is Paginator.State.FullData<*> -> onNewData(state.data, true)
        }
    }

    private fun onNewData(data: List<*>, isFull: Boolean = false) {
        list.isInvisible(false)
        zeroViewHelper?.hide()
        adapter.setData(data, isFull)
    }

    private class SpanSizeLookup constructor(
        private val adapter: ImageAdapter,
        val maxColumns: Int
    ) : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (adapter.getItemViewType(position)) {
                0 -> maxColumns
                else -> 1
            }
        }
    }

    companion object {
        fun calculateNoOfColumns(
            context: Context,
            @DimenRes columnWidthRes: Int
        ): Int {
            val displayMetrics = context.resources.displayMetrics
            val screenWidthPx = displayMetrics.widthPixels / displayMetrics.density
            val columnWidthPx =
                context.resources.getDimension(columnWidthRes) / displayMetrics.density
            return (screenWidthPx / columnWidthPx + 0.5).toInt()// +0.5 for correct rounding to int.
        }
    }
}
