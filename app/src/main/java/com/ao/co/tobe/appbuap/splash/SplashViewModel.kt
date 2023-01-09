package com.ao.co.tobe.appbuap.splash

import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class SplashViewModel : ViewModel() {

    fun splashTime(delay: Long, callback: () -> Unit) {
        Timer().schedule(object : TimerTask() {
            override fun run() = callback()
        }, delay)
    }
}