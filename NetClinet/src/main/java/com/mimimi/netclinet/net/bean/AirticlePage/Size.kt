package com.mimimi.netclinet.net.bean.AirticlePage


import com.google.gson.annotations.SerializedName

data class Size(
    @SerializedName("small")
    val small: String,
    @SerializedName("large")
    val large: String
)