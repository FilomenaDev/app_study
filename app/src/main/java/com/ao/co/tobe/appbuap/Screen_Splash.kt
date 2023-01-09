package com.ao.co.tobe.appbuap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ao.co.tobe.appbuap.controllers.cardReader
import com.ao.co.tobe.appbuap.controllers.deviceEng
import com.ao.co.tobe.appbuap.splash.SplashViewModel
import com.nexgo.oaf.apiv3.APIProxy
import org.koin.androidx.viewmodel.ext.android.viewModel

class Screen_Splash : AppCompatActivity() {
    private val splahsViewModel: SplashViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_splash)

        deviceEng = APIProxy.getDeviceEngine(this@Screen_Splash)
        cardReader = deviceEng!!.cardReader

    }

    override fun onStart() {
        super.onStart()
        splahsViewModel.run {
            splashTime(delay = 3000) {
               startActivity(Intent(this@Screen_Splash,AuthenticationActivity::class.java))
                finish()
            }
        }
    }
}