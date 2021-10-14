package com.example.primarydetail.posts.ui

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

/**
 * This is the lookup class for use in the SelectionAdapter
 *
 * @property recyclerView The RecyclerView the selection adapter is applied to
 */
class PostLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {


    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PostListAdapter.ViewHolder)
                .getItemDetails()
        }
        return null
    }

}
