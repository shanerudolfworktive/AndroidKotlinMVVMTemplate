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
    fun fetchAndDeleteAllTest() {
        Assert.assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)
        viewModel.fetchSocialFeeds()
        Assert.assertEquals(dao.getSocialFeeds().value?.size, 3);

        viewModel.deleteAllSocialFeeds()
        Assert.assertEquals(dao.getSocialFeeds().value?.size, 0);
    }

    @Test
    fun insertAndDeleteTest() {
        Assert.assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)

        val fakeModel = SocialFeedModel("a", -1000, UserModel(-1000, "t", "d"))
        val fakeModel2 = SocialFeedModel("a", -1001, UserModel(-1001, "t", "d"))
        val fakeModel3 = SocialFeedModel("a", -1002, UserModel(-1002, "t", "d"))
        dao.insertSocialFeed(fakeModel)
        dao.insertSocialFeed(fakeModel2)
        dao.insertSocialFeed(fakeModel3)

        Assert.assertEquals(dao.first()?.id, -1000L)
        Assert.assertEquals(dao.getSocialFeeds().value?.size, 3)

        viewModel.deleteSocialFeed(fakeModel)
        Assert.assertEquals(dao.getSocialFeeds().value?.size, 2)
    }

    @Test
    fun cacheStopTest() {
        Assert.assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)

        val fakeModel = SocialFeedModel("a", -1000, UserModel(-1000, "t", "d"))
        dao.insertSocialFeed(fakeModel)

        viewModel.fetchSocialFeeds()

        Assert.assertEquals(dao.getSocialFeeds().value?.size, 1)
    }

    @Test
    fun forceFetchTest() {
        Assert.assertTrue("please use mock variant for testing", dao is FakeSocialFeedsDao)

        val fakeModel = SocialFeedModel("a", -1000, UserModel(-1000, "t", "d"))
        dao.insertSocialFeed(fakeModel)

        viewModel.fetchSocialFeeds(true)

        Assert.assertEquals(dao.getSocialFeeds().value?.size, 4)
    }
}