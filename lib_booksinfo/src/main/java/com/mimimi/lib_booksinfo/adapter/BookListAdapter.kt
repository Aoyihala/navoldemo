package com.mimimi.lib_book.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mimimi.lib_booksinfo.ui.BookInfoActivity
import com.mimimi.lib_booksinfo.R
import com.mimimi.netclinet.net.bean.AirticlePage.AirticlePageBean


/**
 * 列表适配器
 */
class BookListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var datalist = mutableListOf<AirticlePageBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_item,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = datalist.get(position)
        val viewholder = holder as BookItemViewHolder
        Glide.with(viewholder.itemView.context).load(data.cover.plus(data.size.small)).into(viewholder.imgBookCover)
        viewholder.tvBookCreator.text = data.author
        viewholder.tvBookName.text = data.articlename
        viewholder.tvBookCoverCreator.text = data.illustrator
        viewholder.tvBookPoint.text = data.score.average
        viewholder.itemView.setOnClickListener {
            //点击进入详情
            val intent = Intent(viewholder.itemView.context, BookInfoActivity::class.java)
            intent.putExtra("aid",data.articleid)
            viewholder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var tvBookName = itemView.findViewById<TextView>(R.id.tv_book_name)
        var tvBookCreator = itemView.findViewById<TextView>(R.id.tv_book_creator)
        var tvBookCoverCreator = itemView.findViewById<TextView>(R.id.tv_book_cover_creator)
        var tvBookTime = itemView.findViewById<TextView>(R.id.tv_book_time)
        var tvBookPoint = itemView.findViewById<TextView>(R.id.tv_book_point)
        var imgBookCover = itemView.findViewById<ImageView>(R.id.img_book_cover)
    }
}