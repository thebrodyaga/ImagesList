package com.thebrodyaga.imageslist.screen.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.thebrodyaga.imageslist.domain.entity.net.ImageNet
import com.thebrodyaga.imageslist.screen.adapters.delegates.ImageDelegate
import com.thebrodyaga.imageslist.screen.adapters.delegates.ProgressDelegate

class ImageAdapter constructor(
    private val onCardImageClick: (imageNet: ImageNet, sharedElements: Array<Pair<View, String>>) -> Unit,
    private val nextPageCallback: () -> Unit
) : AsyncListDifferDelegationAdapter<Any>(DiffCallback()) {

    private var isFull: Boolean = false

    init {
        delegatesManager.addDelegate(ImageDelegate(onCardImageClick))
        delegatesManager.addDelegate(ProgressDelegate())
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (!isFull && position >= items.size - 10) nextPageCallback.invoke()
    }

    fun setData(list: List<*>, isFull: Boolean = false) {
        this.isFull = isFull
        items = list.toList()
    }

    private class DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
            oldItem == newItem

        override fun getChangePayload(oldItem: Any, newItem: Any) =
            Any() // disable default blink animation

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any) =
            oldItem == newItem
    }
}