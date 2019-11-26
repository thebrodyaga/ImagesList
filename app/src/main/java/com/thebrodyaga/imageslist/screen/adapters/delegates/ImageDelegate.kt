package com.thebrodyaga.imageslist.screen.adapters.delegates

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.thebrodyaga.imageslist.R
import com.thebrodyaga.imageslist.domain.entity.net.ImageNet
import com.thebrodyaga.imageslist.screen.inflate
import kotlinx.android.synthetic.main.item_image.view.*

class ImageDelegate(private val onCardImageClick: (imageNet: ImageNet, sharedElements: Array<Pair<View, String>>) -> Unit) :
    AbsListItemAdapterDelegate<ImageNet, Any, ImageDelegate.ViewHolder>() {

    private val set = ConstraintSet()

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_image))


    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean =
        item is ImageNet

    override fun onBindViewHolder(item: ImageNet, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var item: ImageNet? = null

        init {
            view.root_view.setOnClickListener { v ->
                val pair = Pair(v.image, ViewCompat.getTransitionName(v.image) ?: "")
                item?.also { onCardImageClick.invoke(it, arrayOf(pair)) }
            }
        }

        fun bind(item: ImageNet) = with(itemView) {
            ViewCompat.setTransitionName(image, item.id)
            this@ViewHolder.item = item
            Glide.with(context)
                .load(item.download_url)
                .into(image)
            val ratio = String.format("%d:%d", item.width, item.height)
            set.clone(constraint_layout)
            set.setDimensionRatio(image.id, ratio)
            set.applyTo(constraint_layout)
        }
    }
}