package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("caption")
    val caption: String,
    @SerializedName("shortname")
    val shortname: String
)