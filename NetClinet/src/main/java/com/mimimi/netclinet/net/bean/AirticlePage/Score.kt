package com.mimimi.netclinet.net.bean.AirticlePage


import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("pfnum")
    val pfnum: Int,
    @SerializedName("pfscore")
    val pfscore: Int,
    @SerializedName("average")
    val average: String
)