package com.mimimi.lib_booksinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.bean.CharterBean


class IndexInfoItemAdapter : BaseRecyclerViewAdapter<CharterBean.ChaptersDTO>() {
    override fun gotoOpretionView(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemViewHolder = holder as IndexItemViewHolder
        itemViewHolder.tvName.text = datas.get(position).cname

    }

    override fun initViewHolder(viewGroup: ViewGroup?, viewtype: Int): RecyclerView.ViewHolder {
       var view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.li_indexinfo_item,viewGroup,false)
        return IndexItemViewHolder(view)

    }

    class IndexItemViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val tvName = itemView.findViewById<TextView>(R.id.tv_index_name)

    }
}