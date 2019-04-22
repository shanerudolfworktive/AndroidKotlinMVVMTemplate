package com.example.mvvmtemplate.repo

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mvvmtemplate.BaseTest
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.repository.SocialFeedRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Assert.*
import io.reactivex.android.schedulers.AndroidSchedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class SocialFeedRepositoryTest : BaseTest(){

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    @Mock
    lateinit var repo: SocialFeedRepository

    @Mock
    lateinit var dao: SocialFeedsDao

    @Mock
    lateinit var api: SocialFeedApiService

    @Before
    fun setUp(){
        repo = SocialFeedRepository(dao, api, AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread())
    }

    @Test
    fun testFetchWithData() {
        val fakeSocialFeed = SocialFeedModel("fake_created_at", -1, UserModel(-1, "fake_name", "fake_description"))
        `when`(dao.first()).thenReturn(fakeSocialFeed)
        repo.fetchSocialFeeds()
        verify(api, times(0)).fetchFeeds()
    }

    @Test
    fun testFetchWithoutData() {
        val fakeSocialFeed = SocialFeedModel("fake_created_at", -1, UserModel(-1, "fake_name", "fake_description"))
        val lists = listOf(fakeSocialFeed)
        `when`(dao.getSocialFeeds()).thenReturn(MutableLiveData<List<SocialFeedModel>>())
        `when`(dao.first()).thenReturn(null)
        `when`(api.fetchFeeds()).thenReturn(Observable.just(lists))
        repo.fetchSocialFeeds()
        verify(api, times(1)).fetchFeeds()
        verify(dao, times(1)).insertSocialFeeds(lists)
        assertEquals(repo.fetchFeedsState.value, FetchState.SUCCESS)
    }

}