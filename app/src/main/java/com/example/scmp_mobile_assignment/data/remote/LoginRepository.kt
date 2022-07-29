package com.example.scmp_mobile_assignment.data.remote

import android.util.Log
import com.example.scmp_mobile_assignment.data.model.raw.LoginResponseData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import java.lang.Error

class LoginRepository {
    private  val TAG = LoginRepository::class.java.simpleName

    suspend fun login(email: String, password: String): Flow<LoginResponseData> = callbackFlow {
        Log.d(TAG, "Login")
        val response = NetworkUtils.loginApi(email, password)
        if (response.isSuccess) {
            Log.d(TAG, "login success response : ${response.result}")
            val data = NetworkUtils.gson.fromJson(response.result, LoginResponseData::class.java)
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