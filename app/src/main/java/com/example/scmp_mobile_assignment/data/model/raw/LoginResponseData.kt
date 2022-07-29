package com.example.scmp_mobile_assignment.data.model.raw

import java.io.Serializable

data class LoginResponseData(
    val token: String?,
    val error: String?
): Serializable