package com.example.mvvmtemplate

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.repository.FeedDetailRepository
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class FeedDetailViewModelUnitTest : BaseTest(){
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously


    lateinit var viewModel: FeedDetailViewModel

    @Mock
    lateinit var repo: FeedDetailRepository

    @Captor
    val feedModelCaptor: ArgumentCaptor<SocialFeedModel>? = null

    @Before
    fun setUp(){
        viewModel = FeedDetailViewModel(repo)
    }

    @Test
    fun testInsertSocialFeed() {
        val testDescription = "testDescription"
        val testName= "testName"
        viewModel.description.value = testDescription
        viewModel.name.value = testName

        viewModel.insertSocialFeed()

        verify(repo, times(1)).insertSocialFeed(feedModelCaptor?.capture())
    }
}
