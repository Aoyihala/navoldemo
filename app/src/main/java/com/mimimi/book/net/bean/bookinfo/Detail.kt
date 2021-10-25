package com.mimimi.book.net.bean.bookinfo


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("articleid")
    val articleid: Int,
    @SerializedName("subid")
    val subid: Int,
    @SerializedName("articlename")
    val articlename: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("size")
    val size: Size,
    @SerializedName("intro")
    val intro: String,
    @SerializedName("flag")
    val flag: Int,
    @SerializedName("fullflag")
    val fullflag: Int,
    @SerializedName("display")
    val display: Int,
    @SerializedName("sortid")
    val sortid: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("lastupdate")
    val lastupdate: String,
    @SerializedName("lasttimestamp")
    val lasttimestamp: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("illustrator")
    val illustrator: String,
    @SerializedName("goodnum")
    val goodnum: Int,
    @SerializedName("allvote")
    val allvote: Int,
    @SerializedName("allvisit")
    val allvisit: Int,
    @SerializedName("weekvisit")
    val weekvisit: Int,
    @SerializedName("score")
    val score: Int,
    @SerializedName("pfnum")
    val pfnum: Int,
    @SerializedName("average")
    val average: String
)