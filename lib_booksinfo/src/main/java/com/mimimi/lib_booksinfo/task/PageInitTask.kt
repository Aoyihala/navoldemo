package com.mimimi.lib_book.task

import android.content.Context
import android.graphics.Color
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

import com.mimimi.lib_book.utils.BookCenter
import com.mimimi.lib_booksinfo.utils.SpUtils
import com.mimimi.lib_booksinfo.bean.ContentBean
import com.mimimi.lib_booksinfo.bean.PageBean
import com.mimimi.netclinet.path.Host


class PageInitTask(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val cid = inputData.getString("cid")
        val aid = inputData.getString("aid")
        val vid = inputData.getString("vid")
        val path = inputData.getString("filePath")
        //获取图片集合
        val listpic = BookCenter.getPicListByContent(BookCenter.readContent(path!!)!!,Host.RESULT)
        val listStr = BookCenter.getMaxLineByTextSize(12f,BookCenter.readContent(path)!!,Host.RESULT)
        val listPageBean = mutableListOf<PageBean>()
        listpic.forEach {
            val contentBean = ContentBean()
            contentBean.imgPath = applicationContext.externalCacheDir?.path+"/"+aid+"/"+vid+"/"+it.trim()
            contentBean.type = ContentBean.IMG_TYPE
            val pagebean = PageBean()
            pagebean.contentBean = contentBean
            pagebean.isEveningMode = false
            pagebean.backgroundColor = Color.WHITE
            listPageBean.add(pagebean)
        }



        listStr?.forEach {
            val contentBean = ContentBean()
            contentBean.content = it
            contentBean.type = ContentBean.WORDS_TYPE
            val pagebean = PageBean()
            pagebean.contentBean = contentBean
            pagebean.isEveningMode = false
            pagebean.backgroundColor = Color.WHITE
            listPageBean.add(pagebean)
        }
        //分类存储好
        //这里储存好之后，是不会再去读取本地文件了
        val mapdate = mutableMapOf<String,List<PageBean>>()
        mapdate.put(cid!!,listPageBean)
        if (aid != null) {
            SpUtils.saveCharaptorData(applicationContext,aid,vid!!,cid,listPageBean)
            //代表某本书已经处理完成
            val data = Data.Builder()
                .putString("aid",aid)
                .putString("vid",vid)
                .putString("cid",cid)
                .build()
            return Result.success(data)
        }
        return Result.failure()
    }
}