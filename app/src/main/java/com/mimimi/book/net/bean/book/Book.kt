package com.mimimi.book.net.bean.book

data class Book(
    val articlename: String,
    val articleid: Int,
    val author: String,
    val illustrator: String,
    val sortid: Int,
    val sort: Sort,
    val display: Int,
    val fullflag: Int,
    val score: Score,
    val cover: String,
    val size: Size,
    val lastupdate: String,
    val lasttimestamp: String
)