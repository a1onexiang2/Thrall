package com.neilzheng.thrall

import android.app.Fragment
import android.support.v4.app.Fragment as SupportFragment
import android.support.annotation.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import java.io.Serializable

/**
 * Created by Neil Zheng on 2017/8/16.
 */
class ThrallConfig internal constructor() : Serializable {

    internal var height = UNSETTLED_INT
    internal var paddingLeft = UNSETTLED_INT
    internal var paddingRight = UNSETTLED_INT
    internal var backgroundColor = UNSETTLED_INT
    internal var asSupportActionBar = false
    internal var isInViewPager = false
    internal var visible = true
    internal var elevation = UNSETTLED_FLOAT
    internal var shadowVisible = true
    internal var scrollBehaviorEnabled = false
    internal var toolbarTheme = Theme.DARK()
    internal var popupTheme = Theme.LIGHT()
    internal var titleAppearance = R.style.TextAppearance_AppCompat_Title

    internal var logoIcon = UNSETTLED_INT
    internal var logoSize = intArrayOf(UNSETTLED_INT, UNSETTLED_INT)
    internal var logoVisible = false

    internal var title = UNSETTLED_STRING
    internal var titleGravity = Gravity.CENTER
    internal var titleVisible = true
    internal var titleMargin = intArrayOf(UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT)

    internal var navigationIcon = UNSETTLED_INT
    internal var navigationVisible = false
    internal var navigationOnClickListener: View.OnClickListener = View.OnClickListener {}

    internal var overflowIcon = UNSETTLED_INT

    internal var menuResIds = arrayListOf<Int>()
    internal var menuOnItemClickListener: Toolbar.OnMenuItemClickListener = Toolbar.OnMenuItemClickListener { false }
    internal var menuMinWidth = UNSETTLED_INT
    internal var menuMaxWidth = UNSETTLED_INT
    internal var menuTextAppearance = UNSETTLED_INT
    internal var menuHeight = UNSETTLED_INT
    internal var menuHeightRatio = UNSETTLED_FLOAT

    internal var customView: View? = null
    internal var customViewLayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER)

    private var isSaved = false

    object Theme {
        @JvmStatic @StyleRes fun LIGHT(): Int = R.style.Thrall_Light
        @JvmStatic @StyleRes fun DARK(): Int = R.style.Thrall_Dark
        @JvmStatic @StyleRes fun DAY_NIGHT(): Int = R.style.Thrall_DayNight
    }

    fun setHeight(@Px height: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.height = height
        }
        return this
    }

    fun setPaddingLeft(@Px padding: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.paddingLeft = padding
        }
        return this
    }

    fun setPaddingRight(@Px padding: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.paddingRight = padding
        }
        return this
    }

    fun setAsActionBar(boolean: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            asSupportActionBar = boolean
        }
        return if (boolean) setIsInViewPager(false) else this
    }

    fun setIsInViewPager(boolean: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            isInViewPager = boolean
        }
        return if (boolean) setAsActionBar(false) else this
    }

    fun setBackgroundColor(@ColorInt color: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.backgroundColor = color
        }
        return this
    }

    fun setScrollBehaviorEnabled(enabled: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            scrollBehaviorEnabled = enabled
        }
        return this
    }

    fun setVisible(visible: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.visible = visible
        }
        return this
    }

    fun setElevation(@Px elevation: Float): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.elevation = elevation
        }
        return this
    }

    fun setShadowVisible(visible: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            shadowVisible = visible
        }
        return this
    }

    fun setLogoIcon(@DrawableRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            logoIcon = resId
        }
        return this
    }

    fun setLogoVisible(visible: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            logoVisible = visible
        }
        return this
    }

    fun setLogoSize(size: IntArray): ThrallConfig {
        if (size.size < 2) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            logoSize = size
        }
        return this
    }

    fun setToolbarTheme(@StyleRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            toolbarTheme = resId
        }
        return this
    }

    fun setPopupTheme(@StyleRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            popupTheme = resId
        }
        return this
    }

    fun setTitleAppearance(@StyleRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            titleAppearance = resId
        }
        return this
    }

    fun setTitle(title: String): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.title = title
        }
        return this
    }

    fun setTitleGravity(gravity: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            titleGravity = gravity
        }
        return this
    }

    fun setTitleVisible(visible: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            titleVisible = visible
        }
        return this
    }

    fun setTitleMargin(@Px margin: IntArray): ThrallConfig {
        if (margin.size < 4) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            titleMargin = margin
        }
        return this
    }

    fun setNavigationIcon(@DrawableRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            navigationIcon = resId
        }
        return this
    }

    fun setNavigationVisible(visible: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            navigationVisible = visible
        }
        return this
    }

    fun setNavigationOnClickListener(listener: View.OnClickListener): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            navigationOnClickListener = listener
        }
        return this
    }

    fun setOverflowIcon(@DrawableRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            overflowIcon = resId
        }
        return this
    }

    fun clearMenuResId(): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuResIds.clear()
        }
        return this
    }

    fun addMenuResId(@MenuRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuResIds.add(resId)
        }
        return this
    }

    fun setMenuOnItemClickListener(listener: Toolbar.OnMenuItemClickListener): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuOnItemClickListener = listener
        }
        return this
    }

    fun setMenuTextAppearance(@StyleRes resId: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuTextAppearance = resId
        }
        return this
    }

    fun setMenuMinWidth(@Px minWidth: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            if (this.menuMaxWidth != UNSETTLED_INT && menuMaxWidth < minWidth) {
                //TODO throw ThrallException
                throw ThrallException()
            }
            this.menuMinWidth = minWidth
        }
        return this
    }

    fun setMenuMaxWidth(@Px maxWidth: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            if (this.menuMinWidth != UNSETTLED_INT && menuMinWidth > maxWidth) {
                //TODO throw ThrallException
                throw ThrallException()
            }
            this.menuMaxWidth = maxWidth
        }
        return this
    }

    fun setMenuHeight(@Px height: Int): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuHeight = height
        }
        return this
    }

    fun setMenuHeightRatio(@FloatRange(from = 0.0, to = 1.0) ratio: Float): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuHeightRatio = ratio
        }
        return this
    }

    fun setCustomView(view: View): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.customView = view
        }
        return this
    }

    fun setCustomViewLayoutParams(layoutParams: FrameLayout.LayoutParams): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.customViewLayoutParams = layoutParams
        }
        return this
    }

    fun saveDefault() {
        Checker.check(Inner.instance)
        Inner.instance.isSaved = true
    }

    fun bind(fragment: Fragment) {
        Thrall.bind(fragment, this)
    }

    fun bind(fragment: SupportFragment) {
        Thrall.bind(fragment, this)
    }

    fun bind(activity: AppCompatActivity) {
        Thrall.bind(activity, this)
    }

    internal object Checker {

        private val UNSETTLED_INT = ThrallConfig.UNSETTLED_INT
        private val UNSETTLED_FLOAT = ThrallConfig.UNSETTLED_FLOAT
        private val UNSETTLED_STRING = ThrallConfig.UNSETTLED_STRING

        fun check(config: ThrallConfig) {
            if (isUnsettled(config.height)) {
                config.setHeight(ThrallUtils.dp2px(48))
            }
            if (isUnsettled(config.paddingLeft)) {
                config.setPaddingLeft(0)
            }
            if (isUnsettled(config.paddingRight)) {
                config.setPaddingRight(0)
            }
            if (isUnsettled(config.backgroundColor)) {
                config.setBackgroundColor(0x3F51B5)
            }
            if (isUnsettled(config.logoIcon)) {
                config.setLogoVisible(false)
            }
            if (config.shadowVisible) {
                if (isUnsettled(config.elevation)) {
                    config.setElevation(ThrallUtils.dp2pxf(2))
                }
            }
            if (isUnsettled(config.title)) {
                config.setTitle("")
            }
            if (isUnsettled(config.titleAppearance)) {
                config.setTitleAppearance(R.style.TextAppearance_AppCompat_Title)
            }
            (0..config.titleMargin.size - 1)
                    .filter { isUnsettled(config.titleMargin[it]) }
                    .forEach { config.titleMargin[it] = 0 }
            (0..config.logoSize.size - 1)
                    .filter { isUnsettled(config.logoSize[it]) }
                    .forEach { config.logoSize[it] = 0 }
            if (isUnsettled(config.menuTextAppearance)) {
                config.setMenuTextAppearance(R.style.TextAppearance_AppCompat_Small)
            }
            if (isUnsettled(config.menuHeightRatio)) {
                config.setMenuHeightRatio(1f)
            }
            if (isUnsettled(config.menuHeight)) {
                config.setMenuHeight((config.height * config.menuHeightRatio).toInt())
            }
            if (isUnsettled(config.menuMaxWidth)) {
                config.menuMaxWidth
            }
            if (isUnsettled(config.menuMinWidth)) {
                config.menuMinWidth
            }
        }

        private fun isUnsettled(int: Int): Boolean {
            return int == UNSETTLED_INT
        }

        private fun isUnsettled(str: String): Boolean {
            return str == UNSETTLED_STRING
        }

        private fun isUnsettled(float: Float): Boolean {
            return float == UNSETTLED_FLOAT
        }

        private fun isUnsettled(intArray: IntArray): Boolean {
            return (0..intArray.size - 1).any { isUnsettled(intArray[it]) }
        }
    }

    internal companion object {

        internal val UNSETTLED_INT = -13
        internal val UNSETTLED_FLOAT = -13f
        internal val UNSETTLED_STRING = "unsettled"

        private object Inner {
            val instance = ThrallConfig()
        }

        @JvmStatic internal fun default(): ThrallConfig {
            return Inner.instance
        }
    }

    internal fun cloneInstance(): ThrallConfig {
        val result = ThrallConfig()
        result.height = this.height
        result.paddingLeft = this.paddingLeft
        result.paddingRight = this.paddingRight
        result.backgroundColor = this.backgroundColor
        result.asSupportActionBar = this.asSupportActionBar
        result.isInViewPager = this.isInViewPager
        result.visible = this.visible
        result.elevation = this.elevation
        result.shadowVisible = this.shadowVisible
        result.scrollBehaviorEnabled = this.scrollBehaviorEnabled
        result.toolbarTheme = this.toolbarTheme
        result.popupTheme = this.popupTheme
        result.titleAppearance = this.titleAppearance
        result.logoIcon = this.logoIcon
        result.logoVisible = this.logoVisible
        result.logoSize = this.logoSize
        result.title = this.title
        result.titleGravity = this.titleGravity
        result.titleVisible = this.titleVisible
        result.titleMargin = this.titleMargin
        result.navigationIcon = this.navigationIcon
        result.navigationVisible = this.navigationVisible
        result.navigationOnClickListener = this.navigationOnClickListener
        result.overflowIcon = this.overflowIcon
        result.menuResIds = arrayListOf()
        result.menuResIds.addAll(this.menuResIds)
        result.menuOnItemClickListener = this.menuOnItemClickListener
        result.menuMinWidth = this.menuMinWidth
        result.menuMaxWidth = this.menuMaxWidth
        result.menuTextAppearance = this.menuTextAppearance
        result.menuHeight = this.menuHeight
        result.menuHeightRatio = this.menuHeightRatio
        result.customView = this.customView
        result.customViewLayoutParams = this.customViewLayoutParams
        return result
    }

    private fun checkSavedDefaultLock(): Boolean {
        return isSaved && this === Inner.instance
    }

}