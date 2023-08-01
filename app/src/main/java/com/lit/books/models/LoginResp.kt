package com.lit.books.models
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject
data class LoginResp (
    @SerializedName("data") val userData: JsonObject?,
    @SerializedName("msg") val userMsg: String?,
    @SerializedName("token") val userToken: String?
)