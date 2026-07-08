package com.jeongbj.glim.di

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import com.jeongbj.glim.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY_GLIM)
        FirebaseMessaging.getInstance().register()
    }
}