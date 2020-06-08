package com.provectus.instmedia.ui.fragment.media_list

import android.graphics.drawable.Drawable
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.util.toCorrectDateFormat
import kotlinx.android.synthetic.main.recycler_view_media_list_item.view.*


class MediaListViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(mediaFile: MediaFile, mediaListCallback: MediaListCallback, position: Int) {
        itemView.apply {
            showProgressBar(true)

            captionTextView.text = mediaFile.caption
            dateTextView.text = mediaFile.time.toCorrectDateFormat()
            setPhotoOrVideo(mediaFile.url)
            setCarouselIcon(mediaFile.isCarousel)

            mediaItemContainer.setOnClickListener { mediaListCallback.onMediaClick(mediaFile, position) }
        }
    }

    private fun setPhotoOrVideo(url: String) = if (hasFirstVideo(url)) setVideo(url) else setPhoto(url)

    private fun setCarouselIcon(isCarousel: Boolean) {
        itemView.carouselContainer.visibility = if (isCarousel) View.VISIBLE else View.GONE
    }

    private fun hasFirstVideo(url: String): Boolean = url.contains(URL_VIDEO_SUFFIX)

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

            setOnPreparedListener { mp ->
                mp.setVolume(0F, 0F)
                start()
                showProgressBar(false)
            }

            setOnCompletionListener { mp ->
                mp.reset()
                mp.setVolume(0F, 0F)
                setVideoURI(Uri.parse(url))
                start()
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        itemView.recyclerViewProgressBar.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
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