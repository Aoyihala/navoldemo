package com.mimimi.netclinet.net.bean.AirticlePage


import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("sortid")
    val sortid: Int,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("shortname")
    val shortname: String
)