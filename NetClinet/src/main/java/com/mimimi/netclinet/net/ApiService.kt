package com.mimimi.netclinet.net

import com.mimimi.netclinet.net.bean.Airticle.BookInfo
import com.mimimi.netclinet.net.bean.AirticlePage.AirticlePage
import com.mimimi.netclinet.path.Host
import com.mimimi.netclinet.viewmodels.ViewModelCenter
import com.mimimi.netclinet.viewmodels.home.DateViewModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiService
{
    var api:Api
    init {
        api = Retrofit.Builder().baseUrl(Host.HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }

    interface Api{
        @GET("article/getArticles.php")
        fun getHomePage (
            @Query("appToken") token: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("type") type: String
        ):Call<AirticlePage>

        @GET("article/getArticleInfo.php")
        fun getAirtcleInfo(@Query("aid")aid:Int,
        @Query("appToken")token: String,
        @Query("format")format:Int):Call<BookInfo>
    }

    fun <T> request(path:String, uuid:String):DateViewModel<T>
    {
        return ViewModelCenter.addLiveData(path,uuid)

    }


}