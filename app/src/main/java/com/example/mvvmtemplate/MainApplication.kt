package com.example.mvvmtemplate

import android.app.Application
import android.content.Context
import com.example.mvvmtemplate.di.components.AppComponent
import com.example.mvvmtemplate.di.components.DaggerAppComponent
import com.example.mvvmtemplate.di.modules.AppModule

open class MainApplication : Application(){

    companion object {
        lateinit var appContext: Context
        lateinit var appComponnet: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appComponnet = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
    }
}