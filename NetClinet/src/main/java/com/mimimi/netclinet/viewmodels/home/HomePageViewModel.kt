package com.mimimi.netclinet.viewmodels.home

import androidx.lifecycle.MutableLiveData
import com.mimimi.netclinet.viewmodels.BaseViewModels

/**
 * 通用数据类型model
 */
class DateViewModel<T>:BaseViewModels<T>() {

    var uuid: String?=null
    var onResultCallBack:OnResultCallBack<T>?=null
    init {
        liveData.observeForever {
            onResultCallBack?.onSuccess(it)

        }
    }
    fun callback(onResultCallBack: OnResultCallBack<T>)
    {
        this.onResultCallBack = onResultCallBack

    }

    override fun  getlivedata(): MutableLiveData<T> {
        return liveData
    }


    interface OnResultCallBack<T>{
        fun onError(statue:String)

        fun onSuccess(entity: T)

        fun onLoading(statue: String)
    }

    override fun setStatue(statue: String) {
        this.statuenet = statue
        when(statue)
        {
            NET_WORK_NOT_FOUND->
            {
                onResultCallBack?.onError(statue)
            }
            NET_WORK_ERROR->{
                onResultCallBack?.onError(statue)
            }
            NET_WORK_LOADING->{
                onResultCallBack?.onLoading(statue)
            }


        }


    }

    fun setLiveData(body: T?) {
        this.liveData.postValue(body)
    }


}