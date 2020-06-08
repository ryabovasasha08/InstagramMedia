package com.provectus.instmedia.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient(get(), get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideStethoInterceptor() }
}

fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        stethoInterceptor: StethoInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
            .addNetworkInterceptor(stethoInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return httpLoggingInterceptor
}

fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()