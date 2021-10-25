package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Size(
    @SerializedName("small")
    val small: String,
    @SerializedName("large")
    val large: String
)