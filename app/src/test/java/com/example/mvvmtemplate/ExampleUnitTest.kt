package com.example.mvvmtemplate

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import com.example.mvvmtemplate.data.FakeSocialFeedsDao
import com.example.mvvmtemplate.di.components.DaggerAppComponent
import com.example.mvvmtemplate.di.modules.AppModule
import com.example.mvvmtemplate.model.SocialFeedModel
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


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class ExampleUnitTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    private lateinit var activity: FragmentActivity
    private lateinit var viewModel: FeedDetailViewModel

    @Before
    fun setUp(){
        MainApplication.appComponnet = DaggerAppComponent.builder().appModule(AppModule(Mockito.mock(Context::class.java))).build()
//        this.activity = Robolectric.setupActivity(FragmentActivity::class.java)
//        this.viewModel = ViewModelProviders.of(this.activity)[FeedDetailViewModel::class.java]
        viewModel = FeedDetailViewModel()
        viewModel.repository.dao.deleteAllSocialFeeds()
    }

    @Test
    fun testInserSocialFeed() {
        viewModel.description.value = "testDescription"
        viewModel.name.value = "testName"

        val dao = viewModel.repository.dao

        assertTrue("please use mock variant for testing" ,dao is FakeSocialFeedsDao)
        dao as FakeSocialFeedsDao

        val latch = CountDownLatch(2)//one for initialization, one for actual insert
        dao.mutableFeeds.observeForever{
            latch.countDown()
        }

        viewModel.insertSocialFeed()
        latch.await()

        assertEquals(dao.mutableFeeds.value?.size, 1)
        assertEquals((dao.mutableFeeds.value?.get(-100) as SocialFeedModel).user?.description, "testDescription")
        assertEquals((dao.mutableFeeds.value?.get(-100) as SocialFeedModel).user?.name, "testName")
    }
}
