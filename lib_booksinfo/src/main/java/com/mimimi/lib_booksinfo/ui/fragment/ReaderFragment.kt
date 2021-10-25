package com.mimimi.lib_book.ui.fragment

import android.view.View
import android.widget.TextView
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.ui.fragment.BaseFragment

class lib_bookFragment: BaseFragment() {
    lateinit var textContent:TextView
    lateinit var textFakeContent:TextView
    override fun initview(view: View?) {
        textContent = view?.findViewById(R.id.tv_text_content)!!
        textFakeContent = view?.findViewById(R.id.tv_fake_content)!!
    }


    override fun initdata() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_read
    }

    fun setContent(str:String)
    {
        //textContent.setText(str)
        //这里要对字数进行处理

    }

}