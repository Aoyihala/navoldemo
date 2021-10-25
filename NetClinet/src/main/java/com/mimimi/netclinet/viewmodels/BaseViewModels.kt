package com.mimimi.netclinet.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mimimi.libs.anottention.ViewModelPath
import com.mimimi.netclinet.net.bean.AirticlePage.AirticlePage

abstract class BaseViewModels<T> {
    companion object
    {
        var NET_WORK_LOADING="loading"
        var NET_WORK_ERROR="ERROR"
        var NET_WORK_SUCCESS="SUCCESS"
        var NET_WORK_NOT_FOUND="NOT_FOUND"
        //数据处理完成
        var PARES_DATA_FINISH="PARES_DATA_FINISH"


    }


    protected var statuenet:String = ""
    protected var currentPath:String=""
    protected var liveData:MutableLiveData<T>  = MutableLiveData()
    abstract fun getlivedata():MutableLiveData<T>
    abstract fun setStatue(statue:String)




}