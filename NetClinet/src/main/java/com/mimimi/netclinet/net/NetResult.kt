package com.mimimi.netclinet.net

import com.mimimi.netclinet.viewmodels.ViewModelCenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NetResult<T>(randomUUID: String) :Callback<T>
{
    var uuid:String?=null
    init {
        uuid = randomUUID
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        ViewModelCenter.parseDate<T>(response.body(),uuid,response.code())


    }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }

}