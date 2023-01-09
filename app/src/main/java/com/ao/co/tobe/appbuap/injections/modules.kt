package com.ao.co.tobe.appbuap.injections

import com.ao.co.tobe.appbuap.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModuls = module {

    viewModel { SplashViewModel() }
}