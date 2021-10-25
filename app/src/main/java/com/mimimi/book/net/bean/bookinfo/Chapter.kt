package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("cid")
    val cid: String,
    @SerializedName("chapter")
    val chapter: String
)