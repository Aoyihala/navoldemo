package com.mimimi.lib_book.ui.fragment

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.blankj.utilcode.util.FileUtils
import com.mimimi.lib_book.adapter.IndexListAdapter
import com.mimimi.lib_booksinfo.ui.BookInfoActivity
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.ui.fragment.BaseFragment
import com.mimimi.netclinet.net.bean.Airticle.BookInfo
import com.mimimi.netclinet.net.download.DownLoadEvent
import com.mimimi.netclinet.net.download.GZipEvent

import com.mimimi.reader.utils.Reader
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class IndexFragment: BaseFragment(){
    private lateinit var bookinfo: BookInfo.DataDTO
    var indexListAdapter: IndexListAdapter?=null
    var recyclerView:RecyclerView?=null

    override fun initview(view: View) {
        recyclerView = view.findViewById(R.id.recycler_base)

    }


    override fun initdata() {
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this)
        }
        //对于压缩的处理
        (activity as BookInfoActivity).workCenter.workManager.getWorkInfosByTagLiveData("gzip").observe(this,{
            it.forEach {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        Log.e("解压成功","成功")
                        val aid = it.outputData.getString("aid")
                        val vid = it.outputData.getString("vid")
                        indexListAdapter?.datalist?.forEach {
                            if (it.vid.toString() == vid && indexListAdapter?.articleid == aid?.toInt()) {
                                indexListAdapter?.notifyItemChanged(
                                    indexListAdapter?.datalist?.indexOf(
                                        it
                                    )!!
                                )
                            }
                        }
                        (activity as BookInfoActivity).workCenter.workManager.cancelWorkById(it.id)
                    }
                    WorkInfo.State.ENQUEUED -> {

                    }
                    WorkInfo.State.RUNNING -> {

                    }
                    WorkInfo.State.FAILED -> {

                    }
                    else -> {

                    }
                }
            }
        })
        //对workmananger处理
        (activity as BookInfoActivity).workCenter.workManager.getWorkInfosByTagLiveData("download").observe(this,
            {
                it.forEach {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {

                            //请求网络结束后
                            val aid = it.outputData.getString("aid")
                            val vid = it.outputData.getString("vid")
                            indexListAdapter?.datalist?.forEach {
                                if (it.vid.toString() == vid && indexListAdapter?.articleid == aid?.toInt()) {
                                    it.isSuccess = true
                                    it.path = context?.externalCacheDir?.path + "/" + aid + vid + ".zip"
                                    indexListAdapter?.notifyItemChanged(
                                        indexListAdapter?.datalist?.indexOf(
                                            it
                                        )!!
                                    )
                                }
                            }
                            //移除
                            (activity as BookInfoActivity).workCenter.listUUID.remove(it.id)
                            //开始压缩
                            if (FileUtils.isFileExists(context?.externalCacheDir?.path+"/"+aid+vid+".zip"))
                            {
                                (activity as BookInfoActivity).workCenter.startGzip(aid!!,vid!!)
                            }
                            (activity as BookInfoActivity).workCenter.workManager.cancelWorkById(it.id)

                        }
                        WorkInfo.State.ENQUEUED -> {

                        }
                        WorkInfo.State.RUNNING -> {

                        }
                        WorkInfo.State.FAILED -> {
                            val aid = it.outputData.getString("aid")
                            val vid = it.outputData.getString("vid")
                            val error = it.outputData.getString("error")
                            indexListAdapter?.datalist?.forEach {
                                if (it.vid.toString() == vid && indexListAdapter?.articleid == aid?.toInt()) {
                                    it.isSuccess = false
                                    it.faildReason = if (error!!.isEmpty()) "未知原因,下载失败" else error
                                    indexListAdapter?.notifyItemChanged(
                                        indexListAdapter?.datalist?.indexOf(
                                            it
                                        )!!
                                    )
                                }
                            }
                            (activity as BookInfoActivity).workCenter.listUUID.remove(it.id)
                        }
                        else -> {

                        }
                    }
                }

        })



    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bse
    }

    fun setIndexData(volumes: BookInfo.DataDTO) {
        this.bookinfo = volumes
        indexListAdapter = IndexListAdapter()
        indexListAdapter?.articleid = volumes.detail.articleid
        indexListAdapter?.datalist = volumes.volumes.toMutableList()
        indexListAdapter?.notifyDataSetChanged()
        recyclerView?.adapter = indexListAdapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.setItemAnimator(null);
        indexListAdapter?.onIndexClickListener = object:IndexListAdapter.OnIndexClickListener{
            override fun onClick(
                info: BookInfo.DataDTO.VolumesDTO,
                postion: Int,
                progress: TextView
            ) {
                (activity as BookInfoActivity).workCenter.startNewDownloadTask(volumes.detail.articleid.toString(),info.vid)
                progress.text = "请求中"
            }

        }
        indexListAdapter?.onTextReadListener = object : IndexListAdapter.OnTextReadListener{
            override fun onClickToRead(info: BookInfo.DataDTO.VolumesDTO) {
                //wpub，加密文件
                //不过可以解压，好说，可以先把index文件的目录给抽出来
                Reader().Builder()
                    .setFilePath(context?.externalCacheDir?.path+"/"+indexListAdapter?.articleid+"/"+info.vid)
                    .build()
                    .startReader(context!!)


            }

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun updateProgress(downloadevent: DownLoadEvent)
    {
        var onedata: BookInfo.DataDTO.VolumesDTO? = null
        if (onedata==null)
        {
            indexListAdapter?.datalist?.forEach {
                if (it.vid.toInt().equals(downloadevent.vid))
                {
                    onedata = it

                }
            }
            onedata?.percent = downloadevent.percent
            onedata?.totoal = downloadevent.totoal
            indexListAdapter?.notifyItemChanged(indexListAdapter?.datalist?.indexOf(onedata)!!)
        }
        else
        {
            if (onedata?.vid?.toInt()?.equals(downloadevent.vid)!!)
            {
                onedata?.percent = downloadevent.percent
                onedata?.totoal = downloadevent.totoal
                indexListAdapter?.notifyItemChanged(indexListAdapter?.datalist?.indexOf(onedata)!!)
            }
            else
            {
                //置空
                onedata = null
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun gzipUpdate(gzip:GZipEvent)
    {


    }

}