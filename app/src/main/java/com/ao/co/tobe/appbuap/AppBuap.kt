package com.ao.co.tobe.appbuap

import android.app.Application
import android.os.StrictMode
import com.ao.co.tobe.appbuap.data.place.STPEDatabase
import com.ao.co.tobe.appbuap.injections.mainModuls
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppBuap : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@AppBuap)
            modules(mainModuls)
        }
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )

    }

    val database: STPEDatabase by lazy { STPEDatabase.getDatabase(this) }
    }