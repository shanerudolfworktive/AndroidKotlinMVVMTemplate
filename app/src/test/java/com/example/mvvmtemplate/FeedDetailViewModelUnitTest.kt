package com.example.mvvmtemplate

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import com.example.mvvmtemplate.data.FakeSocialFeedsDao
import com.example.mvvmtemplate.di.components.DaggerAppComponent
import com.example.mvvmtemplate.di.components.DaggerTestAppComponent
import com.example.mvvmtemplate.di.components.TestAppComponent
import com.example.mvvmtemplate.di.modules.AppModule
import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import javax.inject.Inject


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class FeedDetailViewModelUnitTest{

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    private lateinit var activity: FragmentActivity
    private lateinit var viewModel: FeedDetailViewModel

    @Inject lateinit var dao: SocialFeedsDao

    @Before
    fun setUp(){
        MainApplication.appComponnet = DaggerTestAppComponent.builder().appModule(AppModule(Mockito.mock(Context::class.java))).build()
        (MainApplication.appComponnet as TestAppComponent).feedDetailViewModelUnitTest(RepoModule()).inject(this)
        viewModel = FeedDetailViewModel()
        dao.deleteAllSocialFeeds()
    }

    @Test
    fun testInserSocialFeed() {
        viewModel.description.value = "testDescription"
        viewModel.name.value = "testName"

        assertTrue("please use mock variant for testing" ,dao is FakeSocialFeedsDao)
        dao as FakeSocialFeedsDao

        val latch = CountDownLatch(2)//one for initialization, one for actual insert
        (dao as FakeSocialFeedsDao).mutableFeeds.observeForever{
            latch.countDown()
        }

        viewModel.insertSocialFeed()
        latch.await()

        assertEquals((dao as FakeSocialFeedsDao).mutableFeeds.value?.size, 1)
        assertEquals(((dao as FakeSocialFeedsDao).mutableFeeds.value?.get(-100) as SocialFeedModel).user?.description, "testDescription")
        assertEquals(((dao as FakeSocialFeedsDao).mutableFeeds.value?.get(-100) as SocialFeedModel).user?.name, "testName")
    }
}
