package com.example.mvvmtemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.mvvmtemplate.syncing.SocialFeedsWorker
import com.example.mvvmtemplate.util.SyncingUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivityHostFragment) as NavHostFragment?
        NavigationUI.setupWithNavController(bottom_navigation, navHostFragment!!.navController)
        scheduleSync()
    }

    //sync data every day at 2am
    fun scheduleSync() {
        val workManager: WorkManager = WorkManager.getInstance()
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val oneTimeWork = OneTimeWorkRequest.Builder(SocialFeedsWorker::class.java)
            .setConstraints(constraints)
            .setInitialDelay(SyncingUtils.calculateDiffFromNow(2), TimeUnit.MICROSECONDS)
            .build()
        workManager.enqueue(oneTimeWork)
    }
}
