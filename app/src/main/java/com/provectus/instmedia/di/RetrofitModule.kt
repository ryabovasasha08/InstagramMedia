package com.provectus.instmedia.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.provectus.instmedia.Constants.BASE_URL
import com.provectus.instmedia.network.service.AuthenticationService
import com.provectus.instmedia.network.service.MediaService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single { provideRetrofit(get()) }
    single { provideAuthenticationService(get()) }
    single { provideMediaService(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}

fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService = retrofit.create(AuthenticationService::class.java)

fun provideMediaService(retrofit: Retrofit): MediaService = retrofit.create(MediaService::class.java)