package com.mimimi.book.net.bean.outpage

data class PageBean(
    val status: Int,
    val page: Int,
    val pageSize: Int,
    val total: Int,
    val type: String,
    val data:List<Any>,
    val isFromCache:Int
)