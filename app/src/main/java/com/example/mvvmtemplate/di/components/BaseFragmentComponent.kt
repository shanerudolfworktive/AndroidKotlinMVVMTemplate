package com.example.mvvmtemplate.di.components

import com.example.mvvmtemplate.screens.BaseFragment
import dagger.Subcomponent

@Subcomponent
interface BaseFragmentComponent {
    fun inject(baseFragment: BaseFragment)
}