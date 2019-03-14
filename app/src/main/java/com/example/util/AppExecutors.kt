package com.example.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors constructor(
    val diskIO: Executor = DiskIOThreadExecutor(),
    val networkIO: Executor = Executors.newFixedThreadPool(3),
    val mainThread: Executor = MainThreadExecutor()
){
}