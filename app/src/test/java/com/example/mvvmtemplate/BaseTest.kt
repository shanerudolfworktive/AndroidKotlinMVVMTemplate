package com.example.mvvmtemplate

import android.content.Context
import com.example.mvvmtemplate.di.components.DaggerTestAppComponent
import com.example.mvvmtemplate.di.modules.AppModule
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class BaseTest {
    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        MainApplication.appComponnet = DaggerTestAppComponent.builder().appModule(AppModule(context)).build()
    }
}