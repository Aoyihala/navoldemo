package com.mimimi.lib_booksinfo.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.mimimi.lib_booksinfo.task.WorkCenter
import com.mimimi.lib_book.ui.fragment.CiguMiFragment
import com.mimimi.lib_book.ui.fragment.IndexFragment
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.adapter.NormalFragmentAdapter
import com.mimimi.netclinet.net.ApiService
import com.mimimi.netclinet.net.NetResult
import com.mimimi.netclinet.net.bean.Airticle.BookInfo
import com.mimimi.netclinet.path.AppPath
import com.mimimi.netclinet.path.Host
import com.mimimi.netclinet.viewmodels.home.DateViewModel
import kotlinx.android.synthetic.main.activity_book_info.*

import java.util.*

@Route(path =  AppPath.Libs_Book.info_path)
class BookInfoActivity : AppCompatActivity() {
    var aid=0
    lateinit var normalFragmentAdapter: NormalFragmentAdapter
    val indexFragment = IndexFragment()
    val ciguMiFragment = CiguMiFragment()
    val list = mutableListOf<String>()
    val fragments = mutableListOf<Fragment>()
    var uuid = UUID.randomUUID().toString()
    lateinit var workCenter: WorkCenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)
        aid = intent.getIntExtra("aid",0);
        workCenter = WorkCenter(this)
        loaddata()
        normalFragmentAdapter = NormalFragmentAdapter(supportFragmentManager)
        list.add("章节")
        list.add("吐槽")
        fragments.add(indexFragment)
        fragments.add(ciguMiFragment)
        normalFragmentAdapter.titles=list
        normalFragmentAdapter.setFragmentList(fragments)
        normalFragmentAdapter.notifyDataSetChanged()
        view_pager.adapter = normalFragmentAdapter
        tab_layout.setupWithViewPager(view_pager)

    }

    private fun loaddata() {
        ApiService.api.getAirtcleInfo(aid,Host.TEST_APP_TOKEN,1).enqueue(NetResult<BookInfo>(uuid))
        ApiService.request<BookInfo>( AppPath.Libs_Book.info_path,uuid).callback(object : DateViewModel.OnResultCallBack<BookInfo>{
            override fun onError(statue: String) {


            }

            override fun onLoading(statue: String) {

            }

            override fun onSuccess(entity: BookInfo) {
                updateUi(entity)
            }


        })
    }

    private fun updateUi(entity: BookInfo) {
        Glide.with(this).load(entity.data.detail.cover.plus(entity.data.detail.size.large))
            .listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    img_book_bigcover.setImageBitmap(praseBitmap((resource as BitmapDrawable).bitmap))
                    return true
                }

            })
            .into(img_book_bigcover)
        Glide.with(this).load(entity.data.detail.cover.plus(entity.data.detail.size.small))

            .into(img_book_coverinfo)
        tv_book_info_name.text = entity.data.detail.articlename
        tv_book_info_des.text = "  "+entity.data.detail.intro
        indexFragment.setIndexData(entity.data)
        /*val downloadReqsuet = OneTimeWorkRequest.Builder(DownLoadTask::class.java)
            .setInputData(Data.Builder()
                .putString("aid","3055")
                .putString("vid","121555")
                .build()).build();

        WorkManager.getInstance(this).enqueue(downloadReqsuet)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(downloadReqsuet.id).observe(this,{
            Log.e("进度",it.progress.getInt("percent",0).toString())
        })*/


    }


    private fun praseBitmap(bitmap: Bitmap): Bitmap? {

        //创建一个缩小后的bitmap
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false)
        //创建将在ondraw中使用到的经过模糊处理后的bitmap
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        //创建RenderScript，ScriptIntrinsicBlur固定写法
        val rs = RenderScript.create(this)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        //根据inputBitmap，outputBitmap分别分配内存
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

        //设置模糊半径取值0-25之间，不同半径得到的模糊效果不同
        blurScript.setRadius(5f)
        blurScript.setInput(tmpIn)
        blurScript.forEach(tmpOut)

        //得到最终的模糊bitmap
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }





}