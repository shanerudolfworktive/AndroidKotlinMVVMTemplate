package com.example.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

open class MainThreadExecutor : Executor{
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(p0: Runnable?) {
        mainThreadHandler.post(p0)
    }
}