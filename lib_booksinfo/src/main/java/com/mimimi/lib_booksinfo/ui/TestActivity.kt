package com.mimimi.lib_booksinfo.ui


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route

import com.mimimi.lib_book.adapter.BookListAdapter
import com.mimimi.lib_booksinfo.R
import com.mimimi.netclinet.net.ApiService
import com.mimimi.netclinet.net.NetResult
import com.mimimi.netclinet.net.RNYidmTokenModule
import com.mimimi.netclinet.net.bean.AirticlePage.AirticlePage
import com.mimimi.netclinet.net.bean.AirticlePage.AirticlePageBean
import com.mimimi.netclinet.path.AppPath
import com.mimimi.netclinet.path.Host
import com.mimimi.netclinet.viewmodels.home.DateViewModel

import java.util.*

@Route(path = AppPath.Libs_Book.path)
class TestActivity : AppCompatActivity() {
    var airticlePageBeans:MutableList<AirticlePageBean> = mutableListOf()
    var uuid:String = UUID.randomUUID().toString()
    var bookListAdapter = BookListAdapter()

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var imgdownLoad = findViewById<ImageView>(R.id.img_download_mananger)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        recyclerView = findViewById(R.id.recycler_test)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookListAdapter
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when(newState)
                {
                    RecyclerView.SCROLL_STATE_IDLE->
                    {

                    }

                }
            }
        })
        loaddata(1,50)


    }

    private fun loaddata(page:Int,pageSize:Int) {
        Log.e("生成token",RNYidmTokenModule.generateToken(Host.APP_KEY))
        //网络请求
        ApiService.api.getHomePage(
            RNYidmTokenModule.generateToken(Host.APP_KEY),
            page,
            pageSize,
            "update")
            .enqueue(NetResult<AirticlePage>(uuid))
        ApiService.request<AirticlePage>(AppPath.Libs_Book.path,uuid).callback(object : DateViewModel.OnResultCallBack<AirticlePage>{
            override fun onError(statue: String) {
                Log.e("获取数据有误",statue)
            }

            override fun onLoading(statue: String) {


            }

            override fun onSuccess(entity: AirticlePage
            ) {
                entity.let {
                    airticlePageBeans = it.data.toMutableList()
                    bookListAdapter.datalist = airticlePageBeans
                    bookListAdapter.notifyDataSetChanged()
                }


            }

        })

    }

}