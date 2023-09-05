package com.sw.navcomprepeat.core.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sw.navcomprepeat.core.constants.Constants
import java.io.File

fun File.isImage(): Boolean {
    return Regex(Constants.imageReg).containsMatchIn(this.name)
}

fun <T> RecyclerView.Adapter<*>.update(
    oldList: List<T>,
    newList: List<T>,
    compare: (T,T) -> Boolean
) {
    val callback = object : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    val diff = DiffUtil.calculateDiff(callback)

    diff.dispatchUpdatesTo(this)
}