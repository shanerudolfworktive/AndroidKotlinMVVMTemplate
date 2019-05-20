package com.example.mvvmtemplate.repo

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.example.mvvmtemplate.BaseTest
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import com.example.mvvmtemplate.model.remote.apiService.SocialFeedApiService
import com.example.mvvmtemplate.repository.FetchState
import com.example.mvvmtemplate.repository.SocialFeedRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
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

    lateinit var dao: SocialFeedsDao

    @Mock
    lateinit var api: SocialFeedApiService

    @Before
    fun setUp(){
        dao = Room.inMemoryDatabaseBuilder(
            context,
            SocialFeedsLocalDatabase::class.java)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build().socialFeedsDao()
        repo = SocialFeedRepository(dao, api, AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread())
    }

    @Test
    fun testFetchWithData() {
        val fakeSocialFeed = SocialFeedModel("fake_created_at", -1, UserModel(-1, "fake_name", "fake_description"))
        //set up database
        dao.insertSocialFeed(fakeSocialFeed)
        assertThat(dao.first(), equalTo(fakeSocialFeed))

        //setup api
        `when`(api.fetchFeeds()).thenReturn(Observable.just(listOf(fakeSocialFeed)))

        //calling fetch social feeds
        repo.fetchSocialFeeds()

        //when we have local cached data, we should not actually calling api
        verify(api, times(0)).fetchFeeds()
        assertNull(repo.fetchFeedsState.value)

        //force calling the network to invalidate data
        repo.fetchSocialFeeds(true)
        verify(api, times(1)).fetchFeeds()
        assertThat(repo.fetchFeedsState.value, equalTo(FetchState.SUCCESS))
//        assertThat(dao.getSocialFeeds().value?.size, equalTo(1))
//        assertThat(repo.socialFeedModels.value?.size, equalTo(1))
    }

    @Test
    fun testFetchWithoutData() {
        val fakeSocialFeed = SocialFeedModel("fake_created_at", -1, UserModel(-1, "fake_name", "fake_description"))
        val lists = listOf(fakeSocialFeed)
        `when`(api.fetchFeeds()).thenReturn(Observable.just(lists))
        repo.fetchSocialFeeds()
        verify(api, times(1)).fetchFeeds()
        assertThat(dao.first(), equalTo(fakeSocialFeed))
        assertThat(repo.fetchFeedsState.value, equalTo(FetchState.SUCCESS))
//        assertThat(repo.socialFeedModels.value?.size, equalTo(1))
    }

}