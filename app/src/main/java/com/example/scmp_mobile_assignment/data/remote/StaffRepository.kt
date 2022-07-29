package com.example.scmp_mobile_assignment.data.remote

import android.util.Log
import com.example.scmp_mobile_assignment.data.model.raw.StaffResponseData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import java.lang.Error

class StaffRepository {
    private  val TAG = StaffRepository::class.java.simpleName

    fun getStaffList(page: Int): Flow<StaffResponseData> = callbackFlow {
        Log.d(TAG, "getStaffList")
        val response = NetworkUtils.staffListApi(page)
        if (response.isSuccess) {
            Log.d(TAG, "get staff list success response : ${response.result}")
            val data = NetworkUtils.gson.fromJson(response.result, StaffResponseData::class.java)
            offer(data)
        } else {
            val errorMsg = if (JSONObject(
                    response.error?.message ?: ""
                ).isNull("error")
            ) "Network Error" else JSONObject(response.error?.message ?: "").getString("error")
            close(Error(errorMsg))
        }
        awaitClose { channel.close() }
    }
}