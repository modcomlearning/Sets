package com.lit.books.models

import com.google.gson.annotations.SerializedName

data class ForgotChange (
    @SerializedName("phone") val userPhone: String?,
    @SerializedName("newpin") val userPin: String?,
    @SerializedName("otp") val userOtp: String?
        )