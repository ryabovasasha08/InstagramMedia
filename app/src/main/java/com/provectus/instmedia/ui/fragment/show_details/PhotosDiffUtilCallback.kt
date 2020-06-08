package com.provectus.instmedia.ui.fragment.show_details

import androidx.recyclerview.widget.DiffUtil

class PhotosDiffUtilCallback(
        private val oldList: List<String>,
        private val newList: List<String>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}