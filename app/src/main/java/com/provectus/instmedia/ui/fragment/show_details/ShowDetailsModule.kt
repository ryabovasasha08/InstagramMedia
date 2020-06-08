package com.provectus.instmedia.ui.fragment.show_details

import com.provectus.instmedia.entity.MediaFile
import org.koin.dsl.module

val showDetailsModule = module {
    factory { (mediaFile: MediaFile) -> ShowDetailsPresenter(mediaFile, get()) }
}