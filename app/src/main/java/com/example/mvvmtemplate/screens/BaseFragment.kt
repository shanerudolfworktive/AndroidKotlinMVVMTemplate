package com.example.mvvmtemplate.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.di.factories.MainViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment(){

    // FOR DATA
    @Inject lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponnet.inject(this)
    }
}