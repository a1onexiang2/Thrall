package com.neilzheng.thrall

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask


/**
 * Created by Neil Zheng on 2017/8/17.
 */
class ThrallToolbar(context: Context, var attrs: AttributeSet?, var defStyleAttr: Int)
    : Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, null, 0)

    private val container: FrameLayout = FrameLayout(getContext())
    private val titleView: TextView = TextView(getContext())
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var looping = true
    private lateinit var config: ThrallConfig

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        container.x = ((width - container.width) / 2).toFloat()
    }

    override fun setTitle(title: CharSequence) {
        titleView.text = title
    }

    internal fun setConfig(config: ThrallConfig) {
        this.config = config
        context.setTheme(config.toolbarTheme)
        val params = layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = if (config.scrollBehaviorEnabled)
            AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL else 0
        params.height = config.height
        layoutParams = params
        setPadding(config.paddingLeft, 0, config.paddingRight, 0)
        minimumHeight = config.height
        popupTheme = config.popupTheme
        background = ColorDrawable(config.backgroundColor)
        visibility = if (config.visible) View.VISIBLE else View.GONE
        if (config.navigationVisible && config.navigationIcon > 0) {
            updateNavigationView()
        }
        if (config.logoVisible && config.logoIcon > 0) {
            updateLogoView()
        }
        if (config.overflowIcon > 0) {
            overflowIcon = ContextCompat.getDrawable(context, config.overflowIcon)
        }
        updateCenterView()
    }

    private fun updateNavigationView() {
        navigationIcon = ContextCompat.getDrawable(context, config.navigationIcon)
        val imageList = ThrallUtils.findAllView(this, AppCompatImageButton::class.java)
        val navigationView = imageList.filter { it.drawable === navigationIcon }.first()
        val params = navigationView.layoutParams as MarginLayoutParams
        if (config.navigationSize[0] > 0) {
            navigationView.minimumWidth = config.navigationSize[0]
            params.width = config.navigationSize[0]
        }
        if (config.navigationSize[1] > 0) {
            params.height = config.navigationSize[1]
        }
        val marginArr = config.navigationMargin
        params.setMargins(marginArr[0], marginArr[1], marginArr[2], marginArr[3])
        navigationView.layoutParams = params
        val paddingArr = config.navigationPadding
        navigationView.setPadding(paddingArr[0], paddingArr[1], paddingArr[2], paddingArr[3])
        navigationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        navigationView.setOnClickListener(config.navigationOnClickListener)
    }

    private fun updateLogoView() {
        logo = ContextCompat.getDrawable(context, config.logoIcon)
        val imageList = ThrallUtils.findAllView(this, AppCompatImageView::class.java)
        val logoView = imageList.filter { it.drawable === logo }.first()
        val params = logoView.layoutParams as MarginLayoutParams
        if (config.logoSize[0] > 0) {
            params.width = config.logoSize[0]
        }
        if (config.logoSize[1] > 0) {
            params.height = config.logoSize[1]
        }
        val marginArr = config.logoMargin
        params.setMargins(marginArr[0], marginArr[1], marginArr[2], marginArr[3])
        logoView.layoutParams = params
        val paddingArr = config.logoPadding
        logoView.setPadding(paddingArr[0], paddingArr[1], paddingArr[2], paddingArr[3])
        logoView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        logoView.setOnClickListener(config.logoOnClickListener)
    }

    private fun updateCenterView() {
        TextViewCompat.setTextAppearance(titleView, config.titleAppearance)
        titleView.text = config.title
        titleView.gravity = config.titleGravity and Gravity.VERTICAL_GRAVITY_MASK
        val params = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT,
                config.titleGravity and Gravity.HORIZONTAL_GRAVITY_MASK)
        val marginArr = config.titleMargin
        params.setMargins(marginArr[0], marginArr[1], marginArr[2], marginArr[3])
        addView(container, Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT))
        addView(titleView, params)
        if (null != config.customView) {
            container.addView(config.customView, config.customViewLayoutParams)
            container.visibility = View.VISIBLE
            titleView.visibility = View.GONE
        } else {
            container.visibility = View.GONE
            titleView.visibility == if (config.titleVisible) View.VISIBLE else View.GONE
        }
    }

    /**
     * TODO A better implement should be here but currently I don't know how.
     * The lifecycle of an Activity is onCreate() -> onResume -> onCreateOptionsMenu() -> onPrepareOptionsMenu(),
     * and the MenuItemViews are available only after onPrepareOptionsMenu().
     */
    internal fun updateMenuStyle(childCount: Int) {
        val menuView = ThrallUtils.findView(this, ActionMenuView::class.java) ?: return
        timerTask = timerTask {
            if (!looping || isFinished()) {
                if (timerTask != null) {
                    timerTask!!.cancel()
                    timerTask = null
                }
                if (timer != null) {
                    timer!!.cancel()
                    timer = null
                }
                return@timerTask
            }
            val hasOverflow = ThrallUtils.findAllView(menuView, ImageView::class.java)
                    .filter {
                        Class.forName("android.support.v7.widget.ActionMenuPresenter\$OverflowMenuButton")
                                .isInstance(it)
                    }
                    .isNotEmpty()
            if (menuView.childCount == childCount || hasOverflow) {
                looping = false
                (0..menuView.childCount - 1)
                        .map { menuView.getChildAt(it) }
                        .filter { it is ActionMenuItemView }
                        .forEach {
                            updateMenuTextViewStyle(it as ActionMenuItemView)
                        }
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 0, 200)
    }

    private fun isFinished(): Boolean {
        if (context is Activity) {
            val activity = context as Activity
            return (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN && activity.isDestroyed)
                    || activity.isFinishing
        } else {
            return context == null
        }
    }

    private fun updateMenuTextViewStyle(menuItemView: ActionMenuItemView) {
        /**
         *  TODO
         *  but I don't know why.
         */
        post {
            if (config.menuMinWidth > 0) {
                menuItemView.minWidth = config.menuMinWidth
            }
            if (config.menuMaxWidth > 0) {
                menuItemView.maxWidth = config.menuMaxWidth
            }
            val layoutParams = menuItemView.layoutParams as MarginLayoutParams
            layoutParams.height = config.menuHeight
            menuItemView.layoutParams = layoutParams
            TextViewCompat.setTextAppearance(menuItemView, config.menuTextAppearance)
            menuItemView.background = ContextCompat.getDrawable(context, R.drawable.item_background)
        }
        menuItemView.postInvalidate()
    }

}