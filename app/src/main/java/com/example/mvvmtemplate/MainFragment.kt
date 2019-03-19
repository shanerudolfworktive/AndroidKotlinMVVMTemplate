package com.example.mvvmtemplate


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.mvvmtemplate.view.BaseFragment
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import com.example.mvvmtemplate.syncing.SocialFeedsWorker
import java.util.concurrent.TimeUnit
import androidx.work.OneTimeWorkRequest
import com.example.mvvmtemplate.util.SyncingUtils


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(R.id.action_mainFragment_to_socialFeedsFragment)
    }
}
