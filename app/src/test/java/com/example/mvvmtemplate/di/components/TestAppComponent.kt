package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, DataBaseModule::class, APIModule::class, SchedulersModule::class))
@Singleton
interface TestAppComponent : AppComponent{
    fun unitTestComponent(module: RepoModule): UnitTestComponent
}