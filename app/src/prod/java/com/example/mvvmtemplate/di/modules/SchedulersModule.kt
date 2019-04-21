package com.example.mvvmtemplate.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

const val SCHEDULER_IO = "SchedulerIO"
const val SCHEDULER_SINGLE = "SchedulerSingle"
const val SCHEDULER_MAIN = "SchedulerMain"

@Module
class SchedulersModule {
    @Provides
    @Named(SCHEDULER_IO)
    fun provideSchedulerIO() : Scheduler = Schedulers.io()

    @Provides
    @Named(SCHEDULER_SINGLE)
    fun provideSchedulerSingle() : Scheduler = Schedulers.single()

    @Provides
    @Named(SCHEDULER_MAIN)
    fun provideSchedulerMain() : Scheduler = AndroidSchedulers.mainThread()
}