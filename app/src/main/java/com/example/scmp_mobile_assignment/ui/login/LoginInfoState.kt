package com.example.scmp_mobile_assignment.ui.login

import com.example.scmp_mobile_assignment.R

data class LoginInfoState(
    var isValid: Boolean = false,
    var emailError: Int? = null,
    var passwordError: Int? = null
)
