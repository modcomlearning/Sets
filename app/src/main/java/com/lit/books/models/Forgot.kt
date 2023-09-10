package com.lit.books.models

import com.google.gson.annotations.SerializedName

data class Forgot (
    @SerializedName("phone") val userPhone: String?
        )