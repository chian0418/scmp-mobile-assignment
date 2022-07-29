package com.example.scmp_mobile_assignment

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scmp_mobile_assignment.data.remote.NetworkUtils
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SCMPUniteTest {

    @ExperimentalCoroutinesApi
    @Test
    fun testLoginApi() = runBlocking {

        val isSuccess = NetworkUtils.loginApi("eve.holt@reqres.in", "cityslicka").isSuccess

        assertEquals(isSuccess, true)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testStaffApi() = runBlocking {

        val isSuccess = NetworkUtils.staffListApi(1).isSuccess

        assertEquals(isSuccess, true)
    }


}