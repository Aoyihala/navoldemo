package com.mimimi.lib_booksinfo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.bean.ContentBean
import com.mimimi.lib_booksinfo.bean.PageBean
import java.io.File

class ContentPageAdapter: PagerAdapter() {
    var datas:MutableList<PageBean> = mutableListOf()
    override fun getCount(): Int {
        return datas.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView = View.inflate(container.context, R.layout.item_content, null);
        val imgCover = itemView.findViewById<ImageView>(R.id.img_content_pic)
        val textContent = itemView.findViewById<TextView>(R.id.tv_content_info)
        val frameParent = itemView.findViewById<FrameLayout>(R.id.frame_content)
        val onePage = datas.get(position)
        when(onePage.contentBean.type)
        {
            ContentBean.IMG_TYPE->
            {
                //设置imageview
                Glide.with(itemView.context)
                    .load(File(onePage.contentBean.imgPath))
                    .into(imgCover)
            }
            ContentBean.WORDS_TYPE->
            {
                textContent.text = onePage.contentBean.content
            }
        }
        //设置背景颜色
        frameParent.setBackgroundColor(onePage.backgroundColor)
        //夜间模式
        //黑底白字

        container.addView(itemView)
        return itemView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


}