package com.example.mvvmtemplate

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmtemplate.data.FakeSocialFeedsDao
import com.example.mvvmtemplate.di.components.TestAppComponent
import com.example.mvvmtemplate.di.modules.RepoModule
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.viewmodel.FeedDetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
class FeedDetailViewModelUnitTest : BaseTest(){
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    private lateinit var viewModel: FeedDetailViewModel

    @Inject lateinit var dao: SocialFeedsDao

    @Before
    fun setUp(){
        (MainApplication.appComponnet as TestAppComponent).unitTestComponent(RepoModule()).inject(this)
        viewModel = FeedDetailViewModel()
        dao.deleteAllSocialFeeds()
    }

    @Test
    fun testInserSocialFeed() {
        val testDescription = "testDescription"
        val testName= "testName"
        viewModel.description.value = testDescription
        viewModel.name.value = testName

        assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)

        viewModel.insertSocialFeed()

        assertEquals(dao.first()?.user?.description, testDescription)
        assertEquals(dao.first()?.user?.name, testName)
    }
}
