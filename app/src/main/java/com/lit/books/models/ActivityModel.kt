package com.lit.books.models

import com.google.gson.annotations.SerializedName
data class ActivityModel (
    @SerializedName("book_id") val bookId: String?,
    @SerializedName("activity_type") val activityType: String?
)