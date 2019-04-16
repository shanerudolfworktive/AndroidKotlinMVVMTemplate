package com.example.mvvmtemplate.syncing

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mvvmtemplate.repository.SocialFeedRepository

class SocialFeedsWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {
//    private val socialFeedRepository = SocialFeedRepository.getInstance()
    override fun doWork(): Result {
//        socialFeedRepository.fetchSocialFeeds(true)
        return Result.success()
    }
}