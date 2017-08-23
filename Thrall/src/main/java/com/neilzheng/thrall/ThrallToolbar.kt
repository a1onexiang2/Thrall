package com.neilzheng.thrall

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Looper
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by Neil Zheng on 2017/8/17.
 */
class ThrallToolbar(context: Context, var attrs: AttributeSet?, var defStyleAttr: Int)
    : Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, null, 0)

    private val container: FrameLayout = FrameLayout(getContext())
    private val titleView: TextView = TextView(getContext())
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
        minimumHeight = config.height
        context.setTheme(config.toolbarTheme)
        popupTheme = config.popupTheme
        background = ColorDrawable(config.backgroundColor)
        visibility = if (config.visible) View.VISIBLE else View.GONE
        titleView.setTextAppearance(context, config.titleAppearance)
        titleView.text = config.title
        titleView.gravity = config.titleGravity and Gravity.VERTICAL_GRAVITY_MASK
        val titleParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT,
                config.titleGravity and Gravity.HORIZONTAL_GRAVITY_MASK)
        titleParams.setMargins(config.titleMargin[0], config.titleMargin[1],
                config.titleMargin[2], config.titleMargin[3])
        addView(container, Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT))
        addView(titleView, titleParams)
        if (config.navigationVisible && config.navigationIcon > 0) {
            navigationIcon = context.resources.getDrawable(config.navigationIcon)
        }
        if (config.logoVisible && config.logoIcon > 0) {
            logo = context.resources.getDrawable(config.logoIcon)
            val imageList = ThrallUtils.findAllView(this, AppCompatImageView::class.java)
            val logoView = imageList.filter { it.drawable === logo }.first()
            val params = logoView.layoutParams as MarginLayoutParams
            if (config.logoSize[0] > 0) {
                params.width = config.logoSize[0]
            }
            if (config.logoSize[1] > 0) {
                params.height = config.logoSize[1]
            }
            params.leftMargin = config.paddingLeft
            logoView.layoutParams = params
            logoView.setPadding(config.paddingLeft, 0, 0, 0)
        }
        if (config.overflowIcon > 0) {
            overflowIcon = context.resources.getDrawable(config.overflowIcon)
        }
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
        menuView.setPadding(0, 0, ThrallUtils.dp2px(6), 0)
        var waiting = true
        val thread = Thread({
            while (waiting) {
                val hasOverflow = ThrallUtils.findAllView(menuView, ImageView::class.java)
                        .filter {
                            Class.forName("android.support.v7.widget.ActionMenuPresenter\$OverflowMenuButton")
                                    .isInstance(it)
                        }
                        .isNotEmpty()
                if (menuView.childCount == childCount || hasOverflow) {
                    waiting = false
                    (0..menuView.childCount - 1)
                            .map { menuView.getChildAt(it) }
                            .filter { it is ActionMenuItemView }
                            .forEach { updateMenuTextViewStyle(it as ActionMenuItemView) }
                }
            }
        })
        thread.start()
    }

    private fun updateMenuTextViewStyle(menuItemView: ActionMenuItemView) {
        if (config.menuMinWidth > 0) {
            menuItemView.minWidth = config.menuMinWidth
        }
        if (config.menuMaxWidth > 0) {
            menuItemView.maxWidth = config.menuMaxWidth
        }
        val layoutParams = menuItemView.layoutParams as MarginLayoutParams
        layoutParams.height = config.menuHeight
        menuItemView.layoutParams = layoutParams
        menuItemView.setTextAppearance(context, config.menuTextAppearance)
        menuItemView.postInvalidate()
    }

}