package com.mimimi.lib_booksinfo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.mimimi.lib_booksinfo.bean.PageBean
import com.orhanobut.hawk.Hawk


object SpUtils
{



    @SuppressLint("CommitPrefEdits")
    fun saveCharaptorData(context: Context, aid:String, vid:String, cid:String, data:List<PageBean>)
    {



        //这里先取
        var oldmap:MutableMap<String,List<PageBean>>?=null
        val datastrs:String? = Hawk.get(vid)
        if (datastrs!=null&&datastrs.isNotEmpty())
        {
            oldmap =  Gson().fromJson<MutableMap<String,List<PageBean>>>(datastrs,
                object : TypeToken<Map<String,List<PageBean>>>() {}.type)
            if (oldmap==null)
            {
                oldmap = mutableMapOf()
                oldmap.put(cid,data)
            }
            else
            {
                oldmap.put(cid,data)
            }
            Hawk.put(vid,Gson().toJson(oldmap))
        }
        else
        {
            oldmap= mutableMapOf()
            oldmap.put(cid,data)
            Hawk.put(vid,Gson().toJson(oldmap))
        }

    }

    fun getCharatorData(context: Context,aid: String,vid: String):Map<String,List<PageBean>>
    {
        val datas:String = Hawk.get(vid)
        Log.e("获取到的缓存",datas)
        val map:Map<String,List<PageBean>> = Gson().fromJson(datas,
            object : TypeToken<Map<String,List<PageBean>>>() {}.type)
        return map
    }

}