package com.example.scmp_mobile_assignment.data.remote

import android.util.Log
import com.example.scmp_mobile_assignment.data.model.raw.ApiResponseData
import com.example.scmp_mobile_assignment.data.model.raw.LoginRequestData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*
import java.lang.Error
import java.net.HttpURLConnection
import java.net.URL


object NetworkUtils {
    private const val TAG = "Network"
    val gson: Gson = GsonBuilder().create()

    fun loginApi(email: String, password: String): ApiResponseData {
        val httpUrlConnection =
            URL("https://reqres.in/api/login?delay=5").openConnection() as HttpURLConnection

        httpUrlConnection.apply {
            connectTimeout = 10 * 1000
            readTimeout = 10 * 1000
            requestMethod = "POST"
            doOutput = true
            doInput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json");
        }
        val body = gson.toJson(LoginRequestData(email, password))
        OutputStreamWriter(httpUrlConnection.outputStream).apply {
            write(body)
            close()
        }
        val responseCode = httpUrlConnection.responseCode

        Log.d(TAG, "HttpCode : $responseCode")

        val inputStream =
            if (responseCode in 200..399) httpUrlConnection.inputStream else httpUrlConnection.errorStream

        val isReader = InputStreamReader(inputStream)

        val bufReader = BufferedReader(isReader)

        val readTextBuf = StringBuffer()

        var line = bufReader.readLine()

        while (line != null) {
            readTextBuf.append(line)
            line = bufReader.readLine()
        }
        Log.d(TAG, "Response : $readTextBuf")
        return if (responseCode == 200){
            ApiResponseData(true, readTextBuf.toString(), null)
        } else {
            ApiResponseData(false, null, Error(readTextBuf.toString()))
        }
    }



     fun staffListApi(page: Int): ApiResponseData {

        val httpUrlConnection: HttpURLConnection =
            URL("https://reqres.in/api/users?page=${page}").openConnection() as HttpURLConnection


        httpUrlConnection.apply {
            connectTimeout = 10 * 1000
            readTimeout = 10 * 1000
            requestMethod = "GET"
            doInput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json");
        }
        val responseCode = httpUrlConnection.responseCode


        Log.d(TAG, "$responseCode")

        val inputStream =
            if (responseCode in 200..399) httpUrlConnection.inputStream else httpUrlConnection.errorStream
        val readTextBuf = StringBuffer()

        val isReader = InputStreamReader(inputStream)

        val bufReader = BufferedReader(isReader)

        var line = bufReader.readLine()

        while (line != null) {
            readTextBuf.append(line)
            line = bufReader.readLine()
        }
         Log.d(TAG, "Response : $readTextBuf")
        return if (responseCode == 200) {
            ApiResponseData(true, readTextBuf.toString(), null)
        } else {
            ApiResponseData(false, null, Error(readTextBuf.toString()))
        }
    }

}