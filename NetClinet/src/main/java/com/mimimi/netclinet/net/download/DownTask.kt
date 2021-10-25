package com.mimimi.netclinet.net.download

import android.content.Context
import okhttp3.OkHttpClient
import android.R.string.no
import android.util.Log
import androidx.work.*
import com.mimimi.netclinet.path.Host
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File


class DownTask(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams){



    override suspend fun doWork(): Result {

        //https://down.yidm.com/downVolumeWpub.php?aid=3055&vid=121555&token=&appToken=fb79bf05634751acd7dfecde2dee178b.2a076c4f658075a560da3b56a425845c
        val aid = inputData.getString("aid")
        val vid = inputData.getString("vid")
        var isdone = false
        val requestParams = RequestParams(Host.DOWN_LOAD_URL)
        requestParams.addQueryStringParameter("aid",aid)
        requestParams.addQueryStringParameter("vid",vid)
        requestParams.addQueryStringParameter("appToken",Host.TEST_APP_TOKEN)
        x.http().get(requestParams,object:Callback.ProgressCallback<File>{
            override fun onSuccess(result: File?) {

            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {

            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }

            override fun onFinished() {

            }

            override fun onWaiting() {

            }

            override fun onStarted() {

            }

            override fun onLoading(total: Long, current: Long, isDownloading: Boolean) {
                val percent = (100 * current / total).toInt()
                //Log.e("当前进度",percent.toString()+"%")
                //设置进度条
                val data = Data.Builder()
                    .putInt("percent",percent)
                    .build()
                setProgressAsync(data)
                if (percent>=100)
                {
                    isdone = isDownloading
                }
            }
        })
        return Result.retry()
    }



}