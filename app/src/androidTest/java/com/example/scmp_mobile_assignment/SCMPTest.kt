package com.example.scmp_mobile_assignment

import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scmp_mobile_assignment.data.model.raw.LoginResponseData
import com.example.scmp_mobile_assignment.data.model.raw.Staff
import com.example.scmp_mobile_assignment.data.model.raw.StaffResponseData
import com.example.scmp_mobile_assignment.data.remote.LoginRepository
import com.example.scmp_mobile_assignment.data.remote.NetworkUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SCMPTest {

    @ExperimentalCoroutinesApi
    @Test
    fun testLoginApi() = runBlocking {
        val response = async { NetworkUtils.loginApi("eve.holt@reqres.in", "cityslicka") }.await()

        assertEquals(
            response.isSuccess &&
                    !response.result.isNullOrEmpty() &&
                    !JSONObject(response.result!!).isNull("token"), true
        )

    }


    @ExperimentalCoroutinesApi
    @Test
    fun testStaffApi() = runBlocking {
        val response = async { NetworkUtils.staffListApi(1) }.await()
        val staffList = arrayListOf<Staff>()
        try {
            if (response.isSuccess) {
                val data =
                    NetworkUtils.gson.fromJson(response.result, StaffResponseData::class.java)
                staffList.addAll(data.list)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        assertEquals(staffList.size > 0, true)
    }


}