package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("detail")
    val detail: Detail,
    @SerializedName("volumes")
    val volumes: List<Volume>
)