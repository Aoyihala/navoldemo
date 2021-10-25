package com.mimimi.netclinet.net.download

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.os.FileUtils
import android.util.Log
import androidx.work.Data
import com.blankj.utilcode.util.ZipUtils
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class GZipTask(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val aid = inputData.getString("aid")
        val vid = inputData.getString("vid")
        val zipPath = applicationContext.externalCacheDir?.path+"/"+aid + vid+".zip"
        val tempFileName = applicationContext.externalCacheDir?.path+"/"+aid+"/"+vid
        val file = ZipUtils.unzipFile(zipPath,tempFileName)
        if (file.size>0)
        {
            val data = Data.Builder()
                .putString("aid",aid)
                .putString("vid",vid)
                .putString("path",tempFileName)
                .build()
            return Result.success(data)
        }
        else
        {
            return Result.failure()
        }

    }
}