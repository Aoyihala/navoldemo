package com.mimimi.netclinet.net.bean.AirticlePage


import com.google.gson.annotations.SerializedName

data class AirticlePageBean(
    @SerializedName("articlename")
    val articlename: String,
    @SerializedName("articleid")
    val articleid: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("illustrator")
    val illustrator: String,
    @SerializedName("sortid")
    val sortid: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("display")
    val display: Int,
    @SerializedName("fullflag")
    val fullflag: Int,
    @SerializedName("score")
    val score: Score,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("size")
    val size: Size,
    @SerializedName("lastupdate")
    val lastupdate: String,
    @SerializedName("lasttimestamp")
    val lasttimestamp: String
)