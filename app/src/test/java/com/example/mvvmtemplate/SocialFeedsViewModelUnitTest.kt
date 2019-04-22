package com.example.mvvmtemplate

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.repository.SocialFeedRepository
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class SocialFeedsViewModelUnitTest : BaseTest(){
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    private lateinit var viewModel: SocialFeedsViewModel

    @Mock
    lateinit var repo: SocialFeedRepository

    @Before
    fun setUp(){
        viewModel = SocialFeedsViewModel(repo)
    }

    @Test
    fun fetchTest() {
        viewModel.fetchSocialFeeds()
        verify(repo, times(1)).fetchSocialFeeds(false)
        viewModel.fetchSocialFeeds(true)
        verify(repo, times(1)).fetchSocialFeeds(true)
    }

    @Test
    fun deleteAllTest() {
        viewModel.deleteAllSocialFeeds()
        verify(repo, times(1)).deleteAllSocialFeeds()
    }

    @Test
    fun deleteSocialFeedTest() {
        val fakeSocialFeed = SocialFeedModel("fake_created_at", -1, UserModel(-1, "fake_name", "fake_description"))
        viewModel.deleteSocialFeed(fakeSocialFeed)
        verify(repo, times(1)).deleteSocialFeed(fakeSocialFeed)
    }

}