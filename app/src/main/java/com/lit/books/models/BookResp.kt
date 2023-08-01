package com.lit.books.models
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
data class BookResp (
    @SerializedName("data") val userData: JsonArray?,
    @SerializedName("msg") val userMsg: String?
)