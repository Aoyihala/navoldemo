package com.mimimi.reader.utils

import android.content.Context
import android.content.Intent
import com.mimimi.lib_booksinfo.ui.TextReadActivity


class Reader
{

    var imgResult:String = "{\$fullpath$}"
    var filePath:String = ""
    var textSize:String = ""
    lateinit var cotext:Context

    fun startReader(cotext: Context)
    {
        val intent = Intent(cotext, TextReadActivity::class.java)
        intent.putExtra("file",filePath)
        intent.putExtra("result",imgResult)
        intent.putExtra("textSize",textSize)
        cotext.startActivity(intent)
    }
    fun startReader(cotext: Context,path: String)
    {
        filePath=path
        val intent = Intent(cotext, TextReadActivity::class.java)
        intent.putExtra("file",filePath)
        intent.putExtra("result",imgResult)
        intent.putExtra("textSize",textSize)
        cotext.startActivity(intent)
    }


    inner class Builder()
    {
        fun setImgResult(result:String):Builder
        {
            imgResult =   result
            return this
        }

        fun setFilePath(path:String):Builder
        {
            filePath = path
            return this
        }

        fun build():Reader
        {
            return this@Reader
        }

    }
}