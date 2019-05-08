package com.example.mvvmtemplate

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.mvvmtemplate.screens.SocialFeedsFragment
import com.example.mvvmtemplate.utils.ViewMatcherUtils
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SocialFeedsInstrumentedTest {

    @Test
    fun testSuccessRenderWithName() {
        launchFragmentInContainer(Bundle(), R.style.AppTheme) {
            SocialFeedsFragment()
        }
        onView(withId(R.id.recyclerViewSocialFeeds)).check(matches(ViewMatcherUtils.atPosition(0, hasDescendant(withText("fake_name1")))))
        onView(withId(R.id.recyclerViewSocialFeeds)).check(matches(ViewMatcherUtils.atPosition(1, hasDescendant(withText("fake_name2")))))
        onView(withId(R.id.recyclerViewSocialFeeds)).check(matches(ViewMatcherUtils.atPosition(2, hasDescendant(withText("fake_name3")))))
    }
}