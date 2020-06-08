package com.provectus.instmedia.ui.fragment.media_list

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.provectus.instmedia.R
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.util.inflateChildView

class MediaListAdapter : RecyclerView.Adapter<MediaListViewHolder>() {

    private var mediaList = mutableListOf<MediaFile>()

    lateinit var mediaListCallback: MediaListCallback

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): MediaListViewHolder {
        return MediaListViewHolder(viewGroup.inflateChildView(R.layout.recycler_view_media_list_item))
    }

    override fun onBindViewHolder(@NonNull filterViewHolder: MediaListViewHolder, position: Int) {
        filterViewHolder.bind(mediaList[position], mediaListCallback, position)
    }

    override fun getItemCount(): Int = mediaList.size

    fun setData(mediaList: List<MediaFile>) {
        val mediaListDiffResult = DiffUtil.calculateDiff(MediaListDiffUtilCallback(this.mediaList, mediaList))
        this.mediaList.clear()
        this.mediaList.addAll(mediaList)
        mediaListDiffResult.dispatchUpdatesTo(this)
    }

}