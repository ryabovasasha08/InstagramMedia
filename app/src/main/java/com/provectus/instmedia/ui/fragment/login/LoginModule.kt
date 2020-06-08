package com.provectus.instmedia.ui.fragment.login

import android.content.Context
import org.koin.dsl.module

val loginModule = module {
    factory { (context: Context) -> LoginPresenter(context, get(), get(), get()) }
}