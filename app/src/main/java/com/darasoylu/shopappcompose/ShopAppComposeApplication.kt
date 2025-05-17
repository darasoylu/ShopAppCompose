package com.darasoylu.shopappcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopAppComposeApplication: Application() {

    companion object {
        const val TAG = "ShopAppComposeApplication"
    }
}