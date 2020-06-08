package com.provectus.instmedia.ui.activity

import org.koin.dsl.module

val mainModule = module {
    factory { MainPresenter() }
}