package com.mimimi.lib_booksinfo.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Gson gson;
    private List<T> datas = new LinkedList<>();
    private T data;
    private int static_size=0;
    private boolean static_mode;


    public void setStatic_size(int static_size) {
        this.static_size = static_size;
    }

    public void setStatic_mode(boolean static_mode) {
        this.static_mode = static_mode;
    }

    public boolean isStatic_mode() {
        return static_mode;
    }
    public BaseRecyclerViewAdapter()
    {
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return initViewHolder(parent,viewType);
    }


    public T getData() {
        return data;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        gotoOpretionView(holder,position);
    }

    protected abstract void gotoOpretionView(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (isStatic_mode())
        {
            return static_size;
        }
        else
        {
            return datas.size();
        }

    }

    protected abstract RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup,int viewtype);

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public void setData(T data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getDatas()
    {
        return datas;
    }


}
