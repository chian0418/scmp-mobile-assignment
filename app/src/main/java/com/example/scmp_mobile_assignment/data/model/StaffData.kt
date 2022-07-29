package com.example.scmp_mobile_assignment.data.model

sealed class StaffData {
    object Footer : StaffData()
    data class StaffInfo(val avatar: String, val email: String, val name: String) : StaffData()
}