package com.example.mvvmtemplate.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named

const val SCHEDULER_IO = "SchedulerIO"
const val SCHEDULER_SINGLE = "SchedulerSingle"
const val SCHEDULER_MAIN = "SchedulerMain"

@Module
class SchedulersModule {
    @Provides
    @Named(SCHEDULER_IO)
    fun provideSchedulerIO() : Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(SCHEDULER_SINGLE)
    fun provideSchedulerSingle() : Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(SCHEDULER_MAIN)
    fun provideSchedulerMain() : Scheduler = AndroidSchedulers.mainThread()
}