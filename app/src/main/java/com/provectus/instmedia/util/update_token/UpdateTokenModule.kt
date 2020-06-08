package com.provectus.instmedia.util.update_token

import org.koin.dsl.module

val updateTokenModule = module {
    single { UpdateTokenManager(get(), get()) }
}
