package com.provectus.instmedia.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.hasInternetConnection(): Boolean =
        with(this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            return this.getNetworkCapabilities(activeNetwork)?.run {
                    hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                } ?: false
        }