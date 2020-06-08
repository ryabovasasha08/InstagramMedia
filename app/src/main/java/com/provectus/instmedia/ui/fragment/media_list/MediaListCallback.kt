package com.provectus.instmedia.ui.fragment.media_list

import com.provectus.instmedia.entity.MediaFile

interface MediaListCallback {
    fun onMediaClick(mediaFile: MediaFile, position: Int)
}