package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Volume(
    @SerializedName("vid")
    val vid: String,
    @SerializedName("volume")
    val volume: String,
    @SerializedName("chapters")
    val chapters: List<Chapter>
)