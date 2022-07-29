package com.example.scmp_mobile_assignment.data.model.raw

import java.io.Serializable
import java.lang.Error

data class ApiResponseData(
    val isSuccess:Boolean,
    val result :String?,
    val error:Error?,
): Serializable
