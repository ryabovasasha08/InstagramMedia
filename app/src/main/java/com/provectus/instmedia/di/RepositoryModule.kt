package com.provectus.instmedia.di

import com.provectus.instmedia.repository.AuthenticationRepository
import com.provectus.instmedia.repository.MediaRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthenticationRepository(get(), get()) }
    single { MediaRepository(get(), get(), get(), get()) }
}
