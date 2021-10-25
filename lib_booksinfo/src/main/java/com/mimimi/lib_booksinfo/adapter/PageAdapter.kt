package com.mimimi.lib_booksinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.bean.ContentBean
import com.mimimi.lib_booksinfo.bean.PageBean

import java.io.File

class PageAdapter: BaseRecyclerViewAdapter<PageBean>() {
    override fun gotoOpretionView(holder: RecyclerView.ViewHolder?, position: Int) {
        val contentHolder = holder as PageContentBean
        val onePage = datas.get(position)
        when(onePage.contentBean.type)
        {
            ContentBean.IMG_TYPE->
            {
                //设置imageview
                Glide.with(contentHolder.itemView.context)
                    .load(File(onePage.contentBean.imgPath))
                    .into(contentHolder.imgCover)
            }
            ContentBean.WORDS_TYPE->
            {
                contentHolder.textContent.text = onePage.contentBean.content
            }
        }
        //设置背景颜色
        contentHolder.frameParent.setBackgroundColor(onePage.backgroundColor)
        //夜间模式
        //黑底白字




    }

    override fun initViewHolder(viewGroup: ViewGroup?, viewtype: Int): RecyclerView.ViewHolder {
       val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.item_content,viewGroup,false)
        return PageContentBean(view)
    }

    class PageContentBean(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val imgCover = itemView.findViewById<ImageView>(R.id.img_content_pic)
        val textContent = itemView.findViewById<TextView>(R.id.tv_content_info)
        val frameParent = itemView.findViewById<FrameLayout>(R.id.frame_content)



    }
}