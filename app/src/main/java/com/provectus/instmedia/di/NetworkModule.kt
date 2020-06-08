package com.provectus.instmedia.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient(get()) }
    single { provideHttpLoggingInterceptor() }
}

fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return httpLoggingInterceptor
}