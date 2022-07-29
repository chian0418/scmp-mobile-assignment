package com.example.scmp_mobile_assignment.ui.login

data class LoginInfoState(
    var isValid: Boolean = false,
    var emailError: Int? = null,
    var passwordError: Int? = null
)
