package com.example.vkfriends.helpers

import android.app.Application
import android.content.Context
import com.vk.sdk.VKSdk


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(applicationContext)
    }
}