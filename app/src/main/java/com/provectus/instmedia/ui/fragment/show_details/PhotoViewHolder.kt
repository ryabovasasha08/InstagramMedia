package com.provectus.instmedia.ui.fragment.show_details

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.view_holder_photo_item.view.*

class PhotoViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(url: String) {
        showProgressBar(true)
        if (isVideo(url)) setVideo(url) else setPhoto(url)
    }

    private fun isVideo(url: String): Boolean = url.contains(URL_VIDEO_SUFFIX)

    private fun setPhoto(url: String) {
        showPhotoView()
        itemView.apply {
            Glide.with(context!!)
                    .load(url)
                    .listener(onPhotoLoadingListener)
                    .into(photoView)
        }
    }

    private fun setVideo(url: String) {
        showVideoView()

        itemView.videoView.apply {
            setVideoURI(Uri.parse(url))

            setOnPreparedListener {
                start()
                showProgressBar(false)
            }

            setOnCompletionListener { mp ->
                mp.reset()
                setVideoURI(Uri.parse(url))
                start()
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        itemView.viewHolderProgressBar.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun showVideoView() {
        itemView.apply {
            photoView.visibility = View.GONE
            videoView.visibility = View.VISIBLE
        }
    }

    private fun showPhotoView() {
        itemView.apply {
            photoView.visibility = View.VISIBLE
            videoView.visibility = View.GONE
        }
    }

    private val onPhotoLoadingListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            showProgressBar(false)
            return false
        }

    }

    companion object {
        const val URL_VIDEO_SUFFIX = "video"
    }

}