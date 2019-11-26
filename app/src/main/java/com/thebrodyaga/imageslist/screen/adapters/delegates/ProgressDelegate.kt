package com.thebrodyaga.imageslist.screen.adapters.delegates

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.thebrodyaga.imageslist.R
import com.thebrodyaga.imageslist.domain.entity.ui.ProgressItem
import com.thebrodyaga.imageslist.screen.inflate
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class ProgressDelegate constructor() :
    AbsListItemAdapterDelegate<ProgressItem, Any, ProgressDelegate.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_progress))

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean =
        item is ProgressItem

    override fun onBindViewHolder(
        item: ProgressItem,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        val layoutParams =
            holder.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams
        layoutParams?.isFullSpan = true
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}