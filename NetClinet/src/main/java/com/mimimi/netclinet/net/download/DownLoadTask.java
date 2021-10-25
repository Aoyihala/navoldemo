package com.mimimi.netclinet.net.download;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.CoroutineWorker;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mimimi.netclinet.path.Host;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class DownLoadTask extends Worker {

    public DownLoadTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String aid = getInputData().getString("aid");
        String vid =  getInputData().getString("vid");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Host.DOWN_LOAD_URL+"?aid="+aid+"&vid="+vid+"&appToken="+Host.TEST_APP_TOKEN).build();
        BufferedInputStream  bis =null;
        BufferedOutputStream bos=null;

        int count = 0;
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return Result.failure(new Data.Builder().putString("error",response.body().string()).build());
            }

            ResponseBody body = response.body();
            long contentLength = body.contentLength();
            Log.e("文件大小",contentLength+"kybts");
            BufferedSource source = body.source();
            //下载下来其实是zip文件
            File file = new File(getApplicationContext().getExternalCacheDir().getPath()+"/"+aid+vid+".zip");

            InputStream is= response.body().byteStream();
            bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(file);
            bos= new BufferedOutputStream(fos);
            int b = 0;
            byte[] byArr = new byte[1024];
            while((b= bis.read(byArr))!=-1){
                bos.write(byArr, 0, b);
                count+=b;
                int percent = (int) (100*(long)count/contentLength);
                //eventbus
                DownLoadEvent loadEvent = new DownLoadEvent();
                loadEvent.setAid(Integer.valueOf(aid));
                loadEvent.setVid(Integer.valueOf(vid));
                loadEvent.setTotoal(contentLength);
                loadEvent.setPercent(percent);
                EventBus.getDefault().post(loadEvent);
                if (percent>=100)
                {
                    return Result.success(getInputData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Data data = new Data.Builder()
                    .putString("error",e.getLocalizedMessage())
                    .putString("aid",aid)
                    .putString("vid",vid)
                    .build();
            return Result.failure(data);
        }
        finally {

            try {
                assert bis != null;
                bis.close();
                assert bos != null;
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Result.failure(getInputData());
    }





}
