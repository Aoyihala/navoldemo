package com.mimimi.lib_booksinfo.task

import android.content.Context
import android.graphics.Color
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.blankj.utilcode.util.FileUtils
import com.mimimi.lib_book.task.PageInitTask
import com.mimimi.lib_book.utils.BookCenter
import com.mimimi.lib_booksinfo.bean.CharterBean
import com.mimimi.lib_booksinfo.bean.ContentBean
import com.mimimi.lib_booksinfo.bean.EpubData
import com.mimimi.lib_booksinfo.bean.PageBean
import com.mimimi.lib_booksinfo.utils.SpUtils
import com.mimimi.netclinet.net.download.DownLoadTask
import com.mimimi.netclinet.net.download.GZipTask
import com.mimimi.netclinet.path.Host
import java.io.File
import java.util.*

class WorkCenter(context: Context) {

    var workManager: WorkManager = WorkManager.getInstance(context)
    var context = context
    var listUUID:MutableList<UUID> = mutableListOf()

    /**
     * 开始进行指定章节下载任务
     */
    fun startNewDownloadTask(aid: String, vid: String) {
        if (!checkFileExits(context?.externalCacheDir?.path + "/" + aid + vid + ".zip")) {
            //下载
            val downloadReqsuet = OneTimeWorkRequest.Builder(DownLoadTask::class.java)
                .addTag("download")
                .setInputData(Data.Builder()
                    .putString("aid",aid)
                    .putString("vid",vid)
                    .build()).build()
            listUUID.add(downloadReqsuet.id)
            workManager.enqueue(downloadReqsuet)

        }

    }

    /**
     * 开始进行指定章节解压任务
     */
    fun startGzip(aid: String,vid: String)
    {
        //目录不存在
        if (!FileUtils.isDir(context.externalCacheDir?.path + "/" + aid+"/"+vid)) {
            val gzipRequset = OneTimeWorkRequest.Builder(GZipTask::class.java)
                .addTag("gzip")
                .setInputData(Data.Builder().putString("aid",aid)
                    .putString("vid",vid).build())
                .build()
            listUUID.add(gzipRequset.id)
            workManager.enqueue(gzipRequset)
        }
    }

    fun checkFileExits(path: String): Boolean {
        return File(path).exists()
    }

    fun getDownlaodUUids(): MutableList<UUID> {
        return listUUID
    }

    /**
     * vid 卷id
     * aid 文章id
     * cid 章节id
     */
    fun startSplitPages(filePath:String,aid: String,vid: String, cid: String,applicationContext:Context)
    {
        //有存储过程，不需要同时进行的任务
        val file_index = File(filePath+"/"+cid)
        val listrequest = mutableListOf<WorkRequest>()
        if (FileUtils.isFileExists(file_index))
        {
            //建立任务
            //获取图片集合
            val listpic = BookCenter.getPicListByContent(BookCenter.readContent(file_index.path)!!, Host.RESULT)
            val listStr = BookCenter.getMaxLineByTextSize(12f, BookCenter.readContent(file_index.path)!!, Host.RESULT)
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
            SpUtils.saveCharaptorData(applicationContext,aid,vid!!,cid,listPageBean)
        }
    }




}