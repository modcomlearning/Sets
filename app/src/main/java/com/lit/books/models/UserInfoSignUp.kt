package com.lit.books.models

import com.google.gson.annotations.SerializedName
data class UserInfoSignUp (
    @SerializedName("fname") val firstName: String?,
    @SerializedName("lname") val lastName: String?,
    @SerializedName("gender") val userGender: String?,
    @SerializedName("email") val userEmail: String?,
    @SerializedName("phone") val userPhone: String?,
    @SerializedName("password") val userPassword: String?,
    @SerializedName("confirm_password") val confirmuserPassword: String?
)