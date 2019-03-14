package com.example.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DiskIOThreadExecutor : Executor{
    private val diskIO = Executors.newSingleThreadExecutor()
    override fun execute(p0: Runnable?) {
        diskIO.execute(p0)
    }
}