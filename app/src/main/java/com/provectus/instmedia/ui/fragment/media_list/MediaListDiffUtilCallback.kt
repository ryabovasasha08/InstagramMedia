package com.provectus.instmedia.ui.fragment.media_list

import androidx.recyclerview.widget.DiffUtil
import com.provectus.instmedia.entity.MediaFile

class MediaListDiffUtilCallback(
        private val oldList: List<MediaFile>,
        private val newList: List<MediaFile>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}