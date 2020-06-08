package com.provectus.instmedia

import android.app.Application
import com.provectus.instmedia.di.*
import com.provectus.instmedia.ui.activity.mainModule
import com.provectus.instmedia.ui.fragment.login.loginModule
import com.provectus.instmedia.ui.fragment.media_list.mediaListModule
import com.provectus.instmedia.ui.fragment.show_details.showDetailsModule
import com.provectus.instmedia.util.update_token.updateTokenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class InstMediaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        startKoin{
            androidContext(this@InstMediaApplication)
            modules(
                    listOf(
                            networkModule,
                            retrofitModule,
                            repositoryModule,
                            databaseModule,
                            mainModule,
                            updateTokenModule,
                            sharedPreferenceModule,
                            loginModule,
                            mediaListModule,
                            showDetailsModule
                    )
            )
        }
    }

}