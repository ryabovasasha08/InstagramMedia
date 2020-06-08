package com.provectus.instmedia.ui.fragment.show_details

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.provectus.instmedia.R
import com.provectus.instmedia.util.inflateChildView

class PhotoAdapter : RecyclerView.Adapter<PhotoViewHolder>() {

    private var photoList = mutableListOf<String>()

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): PhotoViewHolder {
        return PhotoViewHolder(viewGroup.inflateChildView(R.layout.view_holder_photo_item))
    }

    override fun onBindViewHolder(@NonNull photoViewHolder: PhotoViewHolder, position: Int) {
        photoViewHolder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    fun setData(photoList: List<String>) {
        val photosDiffResult = DiffUtil.calculateDiff(PhotosDiffUtilCallback(this.photoList, photoList))
        this.photoList.clear()
        this.photoList.addAll(photoList)
        photosDiffResult.dispatchUpdatesTo(this)
    }

}