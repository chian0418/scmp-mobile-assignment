package com.example.scmp_mobile_assignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.scmp_mobile_assignment.data.remote.LoginRepository
import com.example.scmp_mobile_assignment.ui.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SCMPUniteTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUpViewModel() {
        loginViewModel = LoginViewModel(LoginRepository())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testLogin() = runBlocking{

        loginViewModel.login("eve.holt@reqres.in", "cityslicka")


        val result = loginViewModel.loginResult.getOrAwaitValue()

        assertEquals(1, 1)

    }
    @Test
    fun testUseAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.scmp_mobile_assignment", appContext.packageName)
    }
}