package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.*
import com.example.mvvmtemplate.screens.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, DataBaseModule::class, APIModule::class, SchedulersModule::class, ViewModelModule::class))
@Singleton
interface AppComponent {
    fun inject(baseFragment: BaseFragment)
}
