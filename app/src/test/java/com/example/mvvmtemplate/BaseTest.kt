package com.example.mvvmtemplate

import android.content.Context
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class BaseTest {
    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }
}