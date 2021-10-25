package com.mimimi.netclinet.net.bean.AirticlePage


import com.google.gson.annotations.SerializedName

data class AirticlePage(
    @SerializedName("status")
    val status: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("data")
    val `data`: List<AirticlePageBean>,
    @SerializedName("isFromCache")
    val isFromCache: Int
)