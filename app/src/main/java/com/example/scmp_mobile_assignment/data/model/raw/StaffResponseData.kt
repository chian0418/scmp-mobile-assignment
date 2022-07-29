package com.example.scmp_mobile_assignment.data.model.raw

import com.google.gson.annotations.SerializedName


data class StaffResponseData(
    @SerializedName(value = "data")
    val list: List<Staff>,
    val page: Int,
    @SerializedName(value = "per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName(value = "total_pages")
    val totalPages: Int
)

data class Staff(
    val avatar: String,
    val email: String,
    @SerializedName(value="first_name")
    val firstName: String,
    val id: Int,
    @SerializedName(value="last_name")
    val lastName: String
)
