package com.provectus.instmedia.ui.fragment.media_list

import org.koin.dsl.module

val mediaListModule = module {
    factory { MediaListPresenter(get(), get()) }
}