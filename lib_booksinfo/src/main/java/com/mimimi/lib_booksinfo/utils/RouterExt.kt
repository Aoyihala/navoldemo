package com.mimimi.lib_book.utils

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter


class RouterExt {
    //可以使用内联函数避开回调写法
    fun go(str:String,context:Context,onSuccess: (() -> Unit) = {},onfalied:(()->Unit)={}){
        ARouter.getInstance().build(str).navigation(context,object : NavigationCallback{
            override fun onFound(postcard: Postcard?) {

            }

            override fun onLost(postcard: Postcard?) {
                onfalied.invoke()
            }

            override fun onArrival(postcard: Postcard?) {
                onSuccess.invoke()
            }

            override fun onInterrupt(postcard: Postcard?) {

            }

        })

    }




}