package com.example.mvvmtemplate.di.modules

import androidx.lifecycle.ViewModel
import com.example.mvvmtemplate.di.components.ViewModelKey
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SocialFeedsViewModel::class)
    abstract fun bindUserViewModel(viewmodel: SocialFeedsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedDetailViewModel::class)
    abstract fun bindSearchViewModel(viewmodel: FeedDetailViewModel): ViewModel
}