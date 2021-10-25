package com.mimimi.netclinet.viewmodels

import androidx.lifecycle.Lifecycle
import com.mimimi.netclinet.viewmodels.home.DateViewModel


/**
 * 具备的能力
 * 1.能够将指定数据处理到对应的viewmodels
 * 2.能够通过页面获取已经初始化好的对应viewmodel
 */
object ViewModelCenter {
    var list = mutableListOf<Any>()

    /**
     * 直接传入body
     */
    fun <T> parseDate(body: T?,uuid:String?,code:Int) {
        for (one in list)
        {
            val onedata = one as DateViewModel<T>
            if (onedata.uuid.equals(uuid))
            {
                onedata.setLiveData(body)
                when(code)
                {
                    200->{
                        onedata.setStatue(BaseViewModels.NET_WORK_SUCCESS)
                    }

                    404->{
                        onedata.setStatue(BaseViewModels.NET_WORK_NOT_FOUND)
                    }

                    500->{
                        onedata.setStatue(BaseViewModels.NET_WORK_ERROR)
                    }


                }

                //移除
                list.remove(onedata)
            }
        }


    }

    fun <T> addLiveData(
        path: String,
        uuid: String
    ): DateViewModel<T> {
        if (!list.isEmpty())
        {
            //移除任务已经完成的容器
          //removeRequestComplete()
        }
        val dateViewModel = DateViewModel<T>()
        dateViewModel.setStatue(BaseViewModels.NET_WORK_LOADING)
        dateViewModel.uuid = uuid
        list.add(dateViewModel)
        //返回当前dateviewmodel
        return dateViewModel

    }


}