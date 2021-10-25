package com.mimimi.lib_booksinfo.ui

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.mimimi.lib_booksinfo.adapter.IndexInfoItemAdapter
import com.mimimi.lib_booksinfo.adapter.PageAdapter

import com.mimimi.lib_booksinfo.task.WorkCenter
import com.mimimi.lib_book.utils.BookCenter
import com.mimimi.lib_booksinfo.R
import com.mimimi.lib_booksinfo.adapter.ContentPageAdapter
import com.mimimi.lib_booksinfo.adapter.NormalFragmentAdapter
import com.mimimi.lib_booksinfo.bean.CharterBean
import com.mimimi.lib_booksinfo.bean.EpubData
import kotlinx.android.synthetic.main.activity_text_read.*

import java.io.File

/**
 * 采取方案为
 * 只加载一章节
 */
class TextReadActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var file:File
    var imgResult:String = " {\$FULLPATH\$}"
    var filePath:String = ""
    var textSize:String = ""
    lateinit var indexItemAdapter: IndexInfoItemAdapter
    var charterBean: CharterBean?=null
    var fragments:MutableList<Fragment> = mutableListOf()
    lateinit var workcenter: WorkCenter
    lateinit var pageAdapter: ContentPageAdapter
    lateinit var viewpagerAdapter: NormalFragmentAdapter
    lateinit var selectindex:CharterBean.ChaptersDTO
    var successcount = 0
    lateinit var  mPageView: RealPageView
      lateinit var mNovelTitleTv: TextView  
      lateinit var mNovelProgressTv: TextView  
      lateinit var mStateTv: TextView  

      lateinit var mTopSettingBarRv: RelativeLayout  
      lateinit var mBottomBarCv: ConstraintLayout  
      lateinit var mBrightnessBarCv: ConstraintLayout  
      lateinit var mSettingBarCv: ConstraintLayout  

     lateinit var mBackIv: ImageView  
     lateinit var mMenuIv: ImageView  
     lateinit var mPreviousChapterTv: TextView  
      lateinit var mNovelProcessSb: SeekBar  
      lateinit var mCatalogProgressTv: TextView  
     lateinit var mNextChapterTv: TextView  
     lateinit var mCatalogIv: ImageView  
     lateinit var mBrightnessIv: ImageView  
     lateinit var mDayAndNightModeIv: ImageView  
     lateinit var mSettingIv: ImageView  
     lateinit var mCatalogTv: TextView  
     lateinit var mBrightnessTv: TextView  
     lateinit var mDayAndNightModeTv: TextView  
     lateinit var mSettingTv: TextView  

      lateinit var mBrightnessProcessSb: SeekBar  
      lateinit var mSystemBrightnessSw: Switch  

      lateinit var mDecreaseFontIv: ImageView  
      lateinit var mIncreaseFontIv: ImageView  
      lateinit var mDecreaseRowSpaceIv: ImageView  
      lateinit var mIncreaseRowSpaceIv: ImageView  
      lateinit var mTheme0: View  
      lateinit var mTheme1: View  
      lateinit var mTheme2: View  
      lateinit var mTheme3: View  
      lateinit var mTheme4: View  
      lateinit var mTurnNormalTv: TextView  
      lateinit var mTurnRealTv: TextView

      private var barcolor = Color.BLACK

      private var mIsLoadingChapter = false // 是否正在加载具体章节

    private var mIsShowingOrHidingBar = false // 是否正在显示或隐藏上下栏

    private var mIsShowBrightnessBar = false // 是否正在显示亮度栏

    private var mIsSystemBrightness = true // 是否为系统亮度

    private var mIsShowSettingBar = false // 是否正在显示设置栏

    private var mIsNeedWrite2Db = true // 活动结束时是否需要将书籍信息写入数据库

    private var mIsUpdateChapter = false // 是否更新章节


    // 从 sp 中读取
    private var mTextSize // 字体大小
            = 0f
    private var mRowSpace // 行距
            = 0f
    private var mTheme // 阅读主题
            = 0
    private var mBrightness // 屏幕亮度，为 -1 时表示系统亮度
            = 0f
    private var mIsNightMode // 是否为夜间模式
            = false
    private var mTurnType // 翻页模式：0 为正常，1 为仿真
            = 0

    private var mMinTextSize = 36f
    private var mMaxTextSize = 76f
    private var mMinRowSpace = 0f
    private var mMaxRowSpace = 48f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_read)

        mNovelTitleTv = findViewById(R.id.tv_read_novel_title)
        mNovelProgressTv = findViewById(R.id.tv_read_novel_progress)
        mStateTv = findViewById(R.id.tv_read_state)
        mSettingTv = findViewById(R.id.tv_read_setting)
        mPageView = findViewById(R.id.pv_read_page_view)
        mSettingTv?.setOnClickListener(this)
        mBackIv = findViewById(R.id.iv_read_back)
        mBackIv.setOnClickListener(this)
        mMenuIv = findViewById(R.id.iv_read_menu)
        mMenuIv.setOnClickListener(this)
        mPreviousChapterTv = findViewById(R.id.tv_read_previous_chapter)
        mPreviousChapterTv.setOnClickListener(this)
        mNextChapterTv = findViewById(R.id.tv_read_next_chapter)
        mNextChapterTv.setOnClickListener(this)
        mCatalogIv = findViewById(R.id.iv_read_catalog)
        mCatalogIv.setOnClickListener(this)
        mBrightnessIv = findViewById(R.id.iv_read_brightness)
        mBrightnessIv.setOnClickListener(this)
        mDayAndNightModeIv = findViewById(R.id.iv_read_day_and_night_mode)
        mDayAndNightModeIv.setOnClickListener(this)
        mSettingIv = findViewById(R.id.iv_read_setting)
        mSettingIv.setOnClickListener(this)
        mCatalogTv = findViewById(R.id.tv_read_catalog)
        mCatalogTv.setOnClickListener(this)
        mBrightnessTv = findViewById(R.id.tv_read_brightness)
        mBrightnessTv.setOnClickListener(this)
        mDayAndNightModeTv = findViewById(R.id.tv_read_day_and_night_mode)
        mDayAndNightModeTv.setOnClickListener(this)
        mTopSettingBarRv = findViewById(R.id.rv_read_top_bar)
        mBottomBarCv = findViewById(R.id.cv_read_bottom_bar)
        mBrightnessBarCv = findViewById(R.id.cv_read_brightness_bar)
        mSettingBarCv = findViewById(R.id.cv_read_setting_bar)
        mDecreaseFontIv = findViewById(R.id.iv_read_decrease_font)
        mDecreaseFontIv.setOnClickListener(this)
        mIncreaseFontIv = findViewById(R.id.iv_read_increase_font)
        mIncreaseFontIv.setOnClickListener(this)
        mDecreaseRowSpaceIv = findViewById(R.id.iv_read_decrease_row_space)
        mDecreaseRowSpaceIv.setOnClickListener(this)
        mIncreaseRowSpaceIv = findViewById(R.id.iv_read_increase_row_space)
        mIncreaseRowSpaceIv.setOnClickListener(this)
        mTheme0 = findViewById(R.id.v_read_theme_0)
        mTheme0.setOnClickListener(this)
        mTheme1 = findViewById(R.id.v_read_theme_1)
        mTheme1.setOnClickListener(this)
        mTheme2 = findViewById(R.id.v_read_theme_2)
        mTheme2.setOnClickListener(this)
        mTheme3 = findViewById(R.id.v_read_theme_3)
        mTheme3.setOnClickListener(this)
        mTheme4 = findViewById(R.id.v_read_theme_4)
        mTheme4.setOnClickListener(this)
        mTurnNormalTv = findViewById(R.id.tv_read_turn_normal)
        mTurnNormalTv.setOnClickListener(this)
        mTurnRealTv = findViewById(R.id.tv_read_turn_real)
        mTurnRealTv.setOnClickListener(this)
        mNovelTitleTv = findViewById(R.id.tv_read_novel_title)
        mNovelProcessSb = findViewById(R.id.sb_read_novel_progress)

      /*  mNovelProcessSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val scale = progress.toDouble() / 100f
                if (mIsUpdateChapter) {
                    if (mType == 0) {   // 网络小说
                        mChapterIndex = ((mNetCatalogList.size - 1) * scale) as Int
                        mCatalogProgressTv.setText(mNetCatalogList.get(mChapterIndex))
                    } else if (mType == 1) {    // 本地 txt
                        mTxtNovelProgress = scale.toFloat()
                        val s = (scale * 100).toString()
                        mCatalogProgressTv.text = s.substring(0, Math.min(5, s.length)) + "%"
                    } else if (mType == 2) {    // 本地 epub
                        mChapterIndex = ((mEpubTocList.size - 1) * scale) as Int
                        mCatalogProgressTv.setText(mEpubTocList.get(mChapterIndex).getTitle())
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mIsUpdateChapter = true
                mCatalogProgressTv.visibility = View.VISIBLE
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mIsUpdateChapter = false
                mCatalogProgressTv.visibility = View.GONE
                if (mType == 0 || mType == 2) {
                    showChapter()
                } else if (mType == 1) {
                    mPageView.jumpWithProgress(mTxtNovelProgress)
                }
            }
        })*/
        mCatalogProgressTv = findViewById(R.id.tv_read_catalog_progress)

        mBrightnessProcessSb = findViewById(R.id.sb_read_brightness_bar_brightness_progress)
      /*  mBrightnessProcessSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (!mIsSystemBrightness) {
                    // 调整亮度
                    mBrightness = progress.toFloat() / 100
                    ScreenUtil.setWindowBrightness(this@ReadActivity, mBrightness)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })*/

        mSystemBrightnessSw = findViewById(R.id.sw_read_system_brightness_switch)
       /* mSystemBrightnessSw.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // 变为系统亮度
                mIsSystemBrightness = true
                mBrightness = -1f
                // 将屏幕亮度设置为系统亮度
                ScreenUtil.setWindowBrightness(
                    this@ReadActivity,
                    ScreenUtil.getSystemBrightness() as Float / ScreenUtil.getBrightnessMax()
                )
            } else {
                // 变为自定义亮度
                mIsSystemBrightness = false
                // 将屏幕亮度设置为自定义亮度
                mBrightness = mBrightnessProcessSb.progress.toFloat() / 100
                ScreenUtil.setWindowBrightness(this@ReadActivity, mBrightness)
            }
        }
*/
        when (mTurnType) {
            0 -> {
                mTurnNormalTv.isSelected = true
                mPageView.setTurnType(PageView.TURN_TYPE.NORMAL)
            }
            1 -> {
                mTurnRealTv.isSelected = true
                mPageView.setTurnType(PageView.TURN_TYPE.REAL)
            }
        }
        initfile()
        initindex()

    }


    private fun initindex() {
        charterBean = BookCenter.getCharatorList(filePath)
        workcenter = WorkCenter(this)
        if (charterBean!=null)
        {
            pageAdapter = ContentPageAdapter()
            indexItemAdapter = IndexInfoItemAdapter()
            indexItemAdapter.datas = charterBean?.chapters
            indexItemAdapter.notifyDataSetChanged()
            //设置章节名称
            mCatalogTv.setText(charterBean?.volumeName)
            initpagevciew()

        }
        else
        {
            //目录文件丢失
            Toast.makeText(this,"此卷目录文件丢失",Toast.LENGTH_SHORT).show()
            //获取所有的text文件
            //没有章节目录，将文本内容拼接

        }



    }

    private fun initpagevciew() {
        mPageView.doOnLayout {
            mPageView.initDrawEpub(BookCenter.getEpublistByVid(this,charterBean!!),0,0)
            mStateTv.visibility = View.GONE
            dayMode()

        }
        mPageView
        mPageView.setPageViewListener(object : PageView.PageViewListener{
            override fun updateProgress(progress: String?) {
                Log.e("阅读进度",progress.toString()+"")
                mNovelProgressTv.setText(progress)


            }

            override fun next() {

            }

            override fun pre() {


            }

            override fun nextPage() {

            }

            override fun prePage() {

            }

            override fun showOrHideSettingBar() {
                if (!mIsShowSettingBar)
                {
                    showBar()
                    showBrightnessBar()
                    showSettingBar()
                    mIsShowSettingBar = true
                }
                else
                {
                    hideBar()
                    hideSettingBar()
                    hideBrightnessBar()
                    hideBar()
                    mIsShowSettingBar = false
                }
            }

        })

    }

    override fun onBackPressed() {
        if (mIsShowSettingBar)
        {
            hideBar()
            hideSettingBar()
            hideBrightnessBar()
        }
        else
        {
            finish()
        }
    }

    private fun readContent() {


    }

    private fun initfile() {
        filePath = intent.getStringExtra("file").toString()

        file = File(filePath)
        if (!file.exists())
        {
            Toast.makeText(this,"此文件不存在,请重新下载",Toast.LENGTH_SHORT).show();
            finish()
        }

    }

    /**
     * 显示上下栏
     */
    private fun showBar() {
        val topAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_top_enter
        )
        topAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                // 结束时重置标记
                mIsShowingOrHidingBar = false
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        val bottomAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_bottom_enter
        )
        mTopSettingBarRv!!.startAnimation(topAnim)
        mBottomBarCv!!.startAnimation(bottomAnim)
        mTopSettingBarRv.visibility = View.VISIBLE
        mBottomBarCv.visibility = View.VISIBLE
    }

    /**
     * 隐藏上下栏
     */
    private fun hideBar() {
        val topExitAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_top_exit
        )
        topExitAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mTopSettingBarRv!!.visibility = View.GONE
                mIsShowingOrHidingBar = false


            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        val bottomExitAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_bottom_exit
        )
        bottomExitAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mBottomBarCv!!.visibility = View.GONE
                BarUtils.setStatusBarColor(window,Color.BLACK)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        mTopSettingBarRv!!.startAnimation(topExitAnim)
        mBottomBarCv!!.startAnimation(bottomExitAnim)
    }

    /**
     * 显示亮度栏
     */
    private fun showBrightnessBar() {
        mIsShowBrightnessBar = true
        val bottomAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_bottom_enter
        )
        mBrightnessBarCv!!.startAnimation(bottomAnim)
        mBrightnessBarCv.visibility = View.VISIBLE
    }

    /**
     * 隐藏亮度栏
     */
    private fun hideBrightnessBar() {
        val bottomExitAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_bottom_exit
        )
        bottomExitAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mBrightnessBarCv!!.visibility = View.GONE
                mIsShowBrightnessBar = false
                BarUtils.setStatusBarColor(window,barcolor)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        mBrightnessBarCv!!.startAnimation(bottomExitAnim)
    }

    /**
     * 显示设置栏
     */
    private fun showSettingBar() {
        mIsShowSettingBar = true
        val bottomAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_bottom_enter
        )
        mSettingBarCv!!.startAnimation(bottomAnim)
        mSettingBarCv.visibility = View.VISIBLE
    }

    /**
     * 隐藏设置栏
     */
    private fun hideSettingBar() {
        val bottomExitAnim = AnimationUtils.loadAnimation(
            this, R.anim.read_setting_bottom_exit
        )
        bottomExitAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mSettingBarCv!!.visibility = View.GONE
                mIsShowSettingBar = false
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        mSettingBarCv!!.startAnimation(bottomExitAnim)
    }

    override fun onClick(p0: View?) {

        when (p0?.getId()) {
            R.id.iv_read_back -> finish()
            R.id.iv_read_menu -> {
            }

            R.id.iv_read_brightness, R.id.tv_read_brightness -> {
                // 隐藏上下栏，并显示亮度栏
                hideBar()
                showBrightnessBar()
            }
            R.id.iv_read_day_and_night_mode, R.id.tv_read_day_and_night_mode -> {
                if (!mIsNightMode) {    // 进入夜间模式
                    nightMode()
                } else {    // 进入日间模式
                    dayMode()
                }
                hideBar()
            }
            R.id.iv_read_setting, R.id.tv_read_setting -> {
                // 隐藏上下栏，并显示设置栏
                hideBar()
                showSettingBar()
            }
            R.id.iv_read_decrease_font -> {
                if (mTextSize == mMinTextSize) {
                     
                }
                mTextSize--
                mPageView.setTextSize(mTextSize)
            }
            R.id.iv_read_increase_font -> {
                if (mTextSize == mMaxTextSize) {
                     
                }
                mTextSize++
                mPageView.setTextSize(mTextSize)
            }
            R.id.iv_read_decrease_row_space -> {
                if (mRowSpace == mMinRowSpace) {
                     
                }
                mRowSpace--
                mPageView.setRowSpace(mRowSpace)
            }
            R.id.iv_read_increase_row_space -> {
                if (mRowSpace == mMaxRowSpace) {
                     
                }
                mRowSpace++
                mPageView.setRowSpace(mRowSpace)
            }
            R.id.v_read_theme_0 -> {
                if (!mIsNightMode && mTheme == 0) {
                     
                }
                mTheme = 0
                updateWithTheme()
            }
            R.id.v_read_theme_1 -> {
                if (!mIsNightMode && mTheme == 1) {
                     
                }
                mTheme = 1
                updateWithTheme()
            }
            R.id.v_read_theme_2 -> {
                if (!mIsNightMode && mTheme == 2) {
                     
                }
                mTheme = 2
                updateWithTheme()
            }
            R.id.v_read_theme_3 -> {
                if (!mIsNightMode && mTheme == 3) {
                     
                }
                mTheme = 3
                updateWithTheme()
            }
            R.id.v_read_theme_4 -> {
                if (!mIsNightMode && mTheme == 4) {
                     
                }
                mTheme = 4
                updateWithTheme()
            }
            R.id.tv_read_turn_normal -> if (mTurnType != 0) {
                mTurnType = 0
                mTurnNormalTv!!.isSelected = true
                mTurnRealTv!!.isSelected = false
                mPageView.setTurnType(PageView.TURN_TYPE.NORMAL)
            }
            R.id.tv_read_turn_real -> if (mTurnType != 1) {
                mTurnType = 1
                mTurnRealTv!!.isSelected = true
                mTurnNormalTv!!.isSelected = false
                mPageView.setTurnType(PageView.TURN_TYPE.REAL)
            }
            else -> {
            }
        }
    }

    /**
     * 进入夜间模式
     */
    private fun nightMode() {
        mIsNightMode = true
        // 取消主题
        mTheme0.isSelected = false
        mTheme1.isSelected = false
        mTheme2.isSelected = false
        mTheme3.isSelected = false
        mTheme4.isSelected = false
        // 设置图标和文字
        mDayAndNightModeIv.setImageResource(R.drawable.read_day)
        mDayAndNightModeTv.text = resources.getString(R.string.read_day_mode)
        // 设置相关颜色
        mNovelTitleTv.setTextColor(resources.getColor(R.color.read_night_mode_title))
        mNovelProgressTv.setTextColor(resources.getColor(R.color.read_night_mode_title))
        mStateTv.setTextColor(resources.getColor(R.color.read_night_mode_text))
        mPageView.setBgColor(resources.getColor(R.color.read_night_mode_bg))
        mPageView.setTextColor(resources.getColor(R.color.read_night_mode_text))
        mPageView.setBackBgColor(resources.getColor(R.color.read_night_mode_back_bg))
        mPageView.setBackTextColor(resources.getColor(R.color.read_night_mode_back_text))
        mPageView.post { mPageView.updateBitmap() }
    }

    /**
     * 进入白天模式
     */
    private fun dayMode() {
        mIsNightMode = false
        // 设置图标和文字
        mDayAndNightModeIv.setImageResource(R.drawable.read_night)
        mDayAndNightModeTv.text = resources.getString(R.string.read_night_mode)
        // 根据主题进行相关设置
        mPageView.post(Runnable { updateWithTheme() })
    }

    /**
     * 根据主题更新阅读界面
     */
    private fun updateWithTheme() {
        if (mIsNightMode) {
            // 退出夜间模式
            mDayAndNightModeIv.setImageResource(R.drawable.read_night)
            mDayAndNightModeTv.text = resources.getString(R.string.read_night_mode)
            mIsNightMode = false
        }
        mTheme0.isSelected = false
        mTheme1.isSelected = false
        mTheme2.isSelected = false
        mTheme3.isSelected = false
        mTheme4.isSelected = false
        var bgColor = resources.getColor(R.color.read_theme_0_bg)
        var textColor = resources.getColor(R.color.read_theme_0_text)
        var backBgColor = resources.getColor(R.color.read_theme_0_back_bg)
        var backTextColor = resources.getColor(R.color.read_theme_0_back_text)
        when (mTheme) {
            0 -> {
                mTheme0.isSelected = true
                bgColor = resources.getColor(R.color.read_theme_0_bg)
                textColor = resources.getColor(R.color.read_theme_0_text)
                backBgColor = resources.getColor(R.color.read_theme_0_back_bg)
                backTextColor = resources.getColor(R.color.read_theme_0_back_text)
            }
            1 -> {
                mTheme1.isSelected = true
                bgColor = resources.getColor(R.color.read_theme_1_bg)
                textColor = resources.getColor(R.color.read_theme_1_text)
                backBgColor = resources.getColor(R.color.read_theme_1_back_bg)
                backTextColor = resources.getColor(R.color.read_theme_1_back_text)
            }
            2 -> {
                mTheme2.isSelected = true
                bgColor = resources.getColor(R.color.read_theme_2_bg)
                textColor = resources.getColor(R.color.read_theme_2_text)
                backBgColor = resources.getColor(R.color.read_theme_2_back_bg)
                backTextColor = resources.getColor(R.color.read_theme_2_back_text)
            }
            3 -> {
                mTheme3.isSelected = true
                bgColor = resources.getColor(R.color.read_theme_3_bg)
                textColor = resources.getColor(R.color.read_theme_3_text)
                backBgColor = resources.getColor(R.color.read_theme_3_back_bg)
                backTextColor = resources.getColor(R.color.read_theme_3_back_text)
            }
            4 -> {
                mTheme4.isSelected = true
                bgColor = resources.getColor(R.color.read_theme_4_bg)
                textColor = resources.getColor(R.color.read_theme_4_text)
                backBgColor = resources.getColor(R.color.read_theme_4_back_bg)
                backTextColor = resources.getColor(R.color.read_theme_4_back_text)
            }
        }
        // 设置相关颜色
        mNovelTitleTv.setTextColor(textColor)
        mNovelProgressTv.setTextColor(textColor)
        mStateTv.setTextColor(textColor)
        mPageView.setTextColor(textColor)
        barcolor = bgColor
        mPageView.setBgColor(bgColor)
        mPageView.setBackTextColor(backTextColor)
        mPageView.setBackBgColor(backBgColor)
        mPageView.updateBitmap()

        if (PageView.IS_TEST) {
            mPageView.setBackgroundColor(bgColor)
            mPageView.invalidate()
        }
    }
}