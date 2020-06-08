package com.provectus.instmedia.di

import android.content.Context
import com.provectus.instmedia.Constants.AGREEMENT_PREFERENCES_KEY
import org.koin.dsl.module

val sharedPreferenceModule = module {
    single(createdAtStart = true) { provideSharedPreferences(get()) }
}

private fun provideSharedPreferences(context: Context) = context.getSharedPreferences(AGREEMENT_PREFERENCES_KEY, Context.MODE_PRIVATE)