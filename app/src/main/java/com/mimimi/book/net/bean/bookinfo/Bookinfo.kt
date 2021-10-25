package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Bookinfo(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val `data`: Info,
    @SerializedName("mtime")
    val mtime: Int
)