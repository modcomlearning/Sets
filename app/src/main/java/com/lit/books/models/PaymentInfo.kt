package com.lit.books.models

import com.google.gson.annotations.SerializedName
data class PaymentInfo (
    @SerializedName("phone") val userPhone: String?,
    @SerializedName("amount") val userAmount: String?,
    @SerializedName("email") val userEmail: String?,
    @SerializedName("book_id") val userBookId: String?
)