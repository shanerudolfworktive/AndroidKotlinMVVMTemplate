package com.example.mvvmtemplate

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmtemplate.data.FakeSocialFeedsDao
import com.example.mvvmtemplate.di.components.TestAppComponent
import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.viewmodel.SocialFeedsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class SocialFeedsViewModelUnitTest : BaseTest(){
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    private lateinit var viewModel: SocialFeedsViewModel

    @Inject
    lateinit var dao: SocialFeedsDao

    @Before
    fun setUp(){
        (MainApplication.appComponnet as TestAppComponent).unitTestComponent(RepoModule()).inject(this)
        viewModel = SocialFeedsViewModel()
        dao.deleteAllSocialFeeds()
    }

    @Test
    fun fetchTest() {
        Assert.assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)

        val latch = CountDownLatch(2)//one for initialization, one for actual insert
        (dao as FakeSocialFeedsDao).feeds.observeForever{
            latch.countDown()
        }

        viewModel.fetchSocialFeeds()
        latch.await()

        Assert.assertEquals(dao.getSocialFeeds().value?.size, 3);
    }

    @Test
    fun deleteTest() {
        Assert.assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)

        val latch = CountDownLatch(2)//one for initialization, one for actual insert
//        dao.insertSocialFeed(SocialFeedModel("a", -1000, UserModel(-1000, "t", "d")))

        viewModel.socialfeedModels.observeForever{
            latch.countDown()
        }

        viewModel.deleteAllSocialFeeds()
        latch.await()

        Assert.assertNull(dao.first())
    }
}