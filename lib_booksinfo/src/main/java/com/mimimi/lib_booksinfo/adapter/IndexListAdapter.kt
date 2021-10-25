package com.mimimi.lib_book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.numberprogressbar.NumberProgressBar
import com.mimimi.lib_booksinfo.R

import com.mimimi.netclinet.net.bean.Airticle.BookInfo

import java.io.File

class IndexListAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var indexviewholder: IndexListViewHolder
    var articleid: Int = 0
    var datalist = mutableListOf<BookInfo.DataDTO.VolumesDTO>()
    lateinit var onIndexClickListener:OnIndexClickListener
    lateinit var onTextReadListener: OnTextReadListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.li_index_item,parent,false)
        val indexListViewHolder  = IndexListViewHolder(view)
        indexListViewHolder.setIsRecyclable(true)
        return indexListViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val indexviewholder = holder as IndexListViewHolder
        this.indexviewholder = indexviewholder
        val dataone = datalist.get(position)
        indexviewholder.tvName.text = dataone.volume
        indexviewholder.tvDown.setOnClickListener {
                onIndexClickListener.onClick(dataone,position,indexviewholder.tvDown)
            }
        indexviewholder.progress.max = 100
        indexviewholder.progress.progress = dataone.percent
        if (dataone.isSuccess)
        {
            if (File(dataone.path).exists())
            {
                if (dataone.isGzip(holder.itemView.context,articleid.toString()))
                {
                    indexviewholder.tvDown.text = "解压成功,点击阅读"
                    indexviewholder.tvDown.setOnClickListener {
                        onTextReadListener.onClickToRead(dataone)
                    }
                    indexviewholder.progress.progress = 100
                }
                else
                {
                    indexviewholder.tvDown.text = "解压中"
                    indexviewholder.tvDown.setOnClickListener {

                    }

                    indexviewholder.progress.progress = 100
                }

            }
            else
            {


                        indexviewholder.tvDown.text = "文件丢失，点击重新下载"
                        indexviewholder.tvDown.setOnClickListener {
                            onIndexClickListener.onClick(dataone,position,indexviewholder.tvDown)
                        }


            }

        }
        else
        {
            //当下载临时标记为false时
            val path:String = holder.itemView.context.externalCacheDir?.path+"/"+articleid+dataone.vid+".zip"
            //非实体判断
            if (File(path).exists()&&File(path).length()>=dataone.totoal)
            {
                if (dataone.isGzip(holder.itemView.context,articleid.toString()))
                {
                    indexviewholder.tvDown.text = "解压成功,点击阅读"
                    indexviewholder.tvDown.setOnClickListener {
                        onTextReadListener.onClickToRead(dataone)
                    }
                    indexviewholder.progress.progress = 100
                }
                else
                {
                    indexviewholder.tvDown.text = "解压中"
                    indexviewholder.tvDown.setOnClickListener {

                    }
                    indexviewholder.progress.progress = 100
                }

            }
            else
            {
                if (dataone.percent>0)
                {
                    indexviewholder.tvDown.text = "正在下载"
                    indexviewholder.tvDown.setOnClickListener {

                    }
                }
                else if (dataone.percent == 100)
                {
                    indexviewholder.tvDown.text = "下载完成"
                    indexviewholder.tvDown.setOnClickListener {

                    }
                }
            }

        }

    }





    override fun getItemCount(): Int {
        return datalist.size
    }




    class IndexListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        var tvName = itemView.findViewById<TextView>(R.id.tv_index_name)
        var progress = itemView.findViewById<NumberProgressBar>(R.id.number_progress_bar)
        var tvDown:TextView = itemView.findViewById(R.id.tv_download_click)

    }

    public interface OnIndexClickListener
    {
       fun onClick(info:BookInfo.DataDTO.VolumesDTO,postion:Int,tvDown:TextView)
    }

    interface  OnTextReadListener
    {
        fun onClickToRead(info: BookInfo.DataDTO.VolumesDTO)
    }


}