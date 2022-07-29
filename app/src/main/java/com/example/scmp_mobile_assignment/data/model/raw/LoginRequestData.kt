package com.example.scmp_mobile_assignment.data.model.raw

import java.io.Serializable


data class LoginRequestData(
    val email:String,
    val password:String
):Serializable
