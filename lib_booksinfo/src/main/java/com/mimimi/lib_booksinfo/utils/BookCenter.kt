package com.mimimi.lib_book.utils

import android.content.Context
import android.util.Log

import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.google.gson.Gson
import com.mimimi.lib_booksinfo.bean.CharterBean
import com.mimimi.lib_booksinfo.bean.EpubData
import com.mimimi.netclinet.path.Host

import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.nio.charset.Charset

/**
 * 处理bookinfo
 */
object BookCenter
{

    fun getCharatorList(context: Context,aid:String,vid:String): CharterBean?
    {
        //前往解压目录下获取
        val path = context.externalCacheDir?.path+"/"+aid+"/"+vid
        if (FileUtils.isDir(path))
        {
            val file = File(path+"/index.opf")
            var result = ""
            if (file.exists())
            {
                //存在,不改名
                try {
                    val length=file.length().toInt()
                    val buff= ByteArray(length)
                    val fin= FileInputStream(file)
                    fin.read(buff);
                    fin.close();
                    result=String(buff, Charset.forName("UTF-8"));
                    return Gson().fromJson(result,CharterBean::class.java)
                }catch (e:Exception){
                    e.printStackTrace();
                }

            }

        }
        return null

    }

    fun readContent(path:String):String?
    {
        val file = File(path)
        var result = ""
        if (file.exists())
        {
            //存在,不改名
            try {
                val length=file.length().toInt()
                val buff= ByteArray(length)
                val fin= FileInputStream(file)
                fin.read(buff);
                fin.close();
                result=String(buff, Charset.forName("UTF-8"));
                return result
            }catch (e:Exception){
                e.printStackTrace();
            }

        }
        return null
    }

    fun getCharatorList(path:String):CharterBean?
    {
        //前往解压目录下获取
        if (FileUtils.isDir(path))
        {
            val file = File(path+"/index.opf")
            var result = ""
            if (file.exists())
            {
                //存在,不改名
                try {
                    val length=file.length().toInt()
                    val buff= ByteArray(length)
                    val fin= FileInputStream(file)
                    fin.read(buff);
                    fin.close();
                    result=String(buff, Charset.forName("UTF-8"));
                    return Gson().fromJson(result,CharterBean::class.java)
                }catch (e:Exception){
                    e.printStackTrace();
                }

            }

        }
        return null

    }
    /**
     * 传入卷数据
     *
     */
    fun getEpublistByVid(context: Context,chaptor:CharterBean):List<EpubData>
    {
        var epubDatas = mutableListOf<EpubData>()
        var path = File(context.externalCacheDir?.path+"/"+chaptor.aid+"/"+chaptor.vid)
        chaptor.chapters.forEach {
            //具体每一章节的文件路径
            var cpath = path.path+"/"+it.cid
            //插图筛选出来
            val listpic = getPicListByContent(readContent(cpath)!!, Host.RESULT)
            listpic.forEach{
                epubDatas.add(EpubData(path.path+"/"+it.trim(), EpubData.TYPE.IMG))
            }
            epubDatas.add(EpubData(readContent(cpath), EpubData.TYPE.TEXT))
        }
        return epubDatas

    }

    fun getPicListByContent(content: String,result: String):List<String>
    {
        val lines = content.reader().readLines().toMutableList()
        val piclines = mutableListOf<String>()
        for (oneline in lines)
        {
            if (oneline.contains(result))
            {
                //替换
                    oneline.trim().replace(result.trim()," ")
                val star =  oneline.trim().replace(result.trim()," ")
                piclines.add(star)
            }
        }
        return piclines
    }

    fun getMaxLineByTextSize(size:Float,content:String,result:String): List<String?>? {
        //按照规则会将pic所在的位置行数移除
        val lines = content.reader().readLines().toMutableList()
        val needsremoveline = mutableListOf<String>()
        for (oneline in lines)
        {
            if (oneline.contains(result))
            {
                //移除
                needsremoveline.add(oneline)
            }
        }
        lines.removeAll(needsremoveline)
        val resizecontent:StringBuffer= StringBuffer()
        //重新组织为字符串
        for (str in lines)
        {
            resizecontent.append(str)
        }
        //计算当前剩余文本所需要展示的总面积

        val contentminji = resizecontent?.length!! * SizeUtils.sp2px(size)
        //计算当前屏幕展示面积
        val oneScreenmj = ScreenUtils.getScreenHeight()*ScreenUtils.getScreenWidth()
        //计算所需要的行数
        var reallines = oneScreenmj/contentminji
        Log.e("当前文本长度",resizecontent.length.toString()+"字")
        if (oneScreenmj%contentminji>0)
        {
            //余数
            reallines += 1
        }
        Log.e("当前文本所需要展示的行数",reallines.toString()+"行")
        //计算一行多少字
        val onelinewordslength = ScreenUtils.getScreenWidth()/ SizeUtils.sp2px(size)
        val listEnd = getStrList(resizecontent.toString(),onelinewordslength*reallines)

        return lines

    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     * @param inputString  原始字符串
     * @param length   指定长度
     * @return
     */
    fun getStrList(inputString: String, length: Int): List<String?>? {
        var size = inputString.length / length
        if (inputString.length % length != 0) {
            size += 1
        }
        return getStrList(inputString, length, size)
    }


    /**
     * 把原始字符串分割成指定长度的字符串列表
     * @param inputString 原始字符串
     * @param length 指定长度
     * @param size  指定列表大小
     * @return
     */
    fun getStrList(
        inputString: String, length: Int,
        size: Int
    ): List<String?>? {
        val list: MutableList<String?> = ArrayList()
        for (index in 0 until size) {
            val childStr = substring(
                inputString, index * length,
                (index + 1) * length
            )
            list.add(childStr)
        }
        return list

    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     * @param str  原始字符串
     * @param f 开始位置
     * @param t  结束位置
     * @return
     */
    fun substring(str: String, f: Int, t: Int): String? {
        if (f > str.length) return null
        return if (t > str.length) {
            str.substring(f, str.length)
        } else {
            str.substring(f, t)
        }
    }


}