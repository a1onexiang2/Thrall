package com.neilzheng.thrall

import android.app.Fragment
import android.content.Context
import android.support.v4.app.Fragment as SupportFragment
import android.support.annotation.*
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
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
    internal var fitSystem = true
    internal var asActionBar = false
    internal var isInViewPager = false
    internal var visible = true
    internal var elevation = UNSETTLED_FLOAT
    internal var shadowVisible = true
    internal var scrollBehaviorEnabled = false
    internal var toolbarTheme = Theme.DARK
    internal var popupTheme = Theme.LIGHT
    internal var titleAppearance = R.style.TextAppearance_AppCompat_Title

    internal var logoIcon = UNSETTLED_INT
    internal var logoSize = intArrayOf(UNSETTLED_INT, UNSETTLED_INT)
    internal var logoPadding = intArrayOf(UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT)
    internal var logoMargin = intArrayOf(UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT)
    internal var logoVisible = false
    internal var logoOnClickListener: (View) -> Unit = {}

    internal var title = UNSETTLED_STRING
    internal var titleGravity = Gravity.CENTER
    internal var titleVisible = true
    internal var titleMargin = intArrayOf(UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT)

    internal var navigationIcon = UNSETTLED_INT
    internal var navigationSize = intArrayOf(UNSETTLED_INT, UNSETTLED_INT)
    internal var navigationPadding = intArrayOf(UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT)
    internal var navigationMargin = intArrayOf(UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT, UNSETTLED_INT)
    internal var navigationVisible = false
    internal var navigationOnClickListener: (View) -> Unit = { view ->
        ThrallUtils.getActivityFromView(view)?.finish()
    }

    internal var overflowIcon = UNSETTLED_INT

    internal var menuResIds = arrayListOf<Int>()
    internal var menuOnItemClickListener: (MenuItem) -> Boolean = { false }

    internal var customView: View? = null
    internal var customViewLayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER)

    private var isSaved = false

    object Theme {
        @JvmField
        @StyleRes
        val LIGHT: Int = R.style.Thrall_Light
        @JvmField
        @StyleRes
        val DARK: Int = R.style.Thrall_Dark
        @JvmField
        @StyleRes
        val DAY_NIGHT: Int = R.style.Thrall_DayNight
    }

    fun loadStyle(context: Context, @StyleRes resId: Int): ThrallConfig {
        if (checkSavedDefaultLock()) return this
        val ta = context.obtainStyledAttributes(resId, R.styleable.Thrall)
        setHeight(ta.getDimensionPixelOffset(R.styleable.Thrall_height, height))
        setPaddingLeft(ta.getDimensionPixelOffset(R.styleable.Thrall_paddingLeft, paddingLeft))
        setPaddingRight(ta.getDimensionPixelOffset(R.styleable.Thrall_paddingLeft, paddingRight))
        setBackgroundColor(ta.getColor(R.styleable.Thrall_backgroundColor, backgroundColor))
        setFitSystem(ta.getBoolean(R.styleable.Thrall_fitSystem, fitSystem))
        setAsActionBar(ta.getBoolean(R.styleable.Thrall_asActionBar, asActionBar))
        setIsInViewPager(ta.getBoolean(R.styleable.Thrall_isInViewPager, isInViewPager))
        setVisible(ta.getBoolean(R.styleable.Thrall_visible, visible))
        setElevation(ta.getDimension(R.styleable.Thrall_elevation, elevation))
        setShadowVisible(ta.getBoolean(R.styleable.Thrall_shadowVisible, shadowVisible))
        setScrollBehaviorEnabled(ta.getBoolean(R.styleable.Thrall_scrollBehaviorEnabled, scrollBehaviorEnabled))
        setToolbarTheme(ta.getResourceId(R.styleable.Thrall_toolbarTheme, toolbarTheme))
        setPopupTheme(ta.getResourceId(R.styleable.Thrall_popupTheme, popupTheme))
        setTitleAppearance(ta.getResourceId(R.styleable.Thrall_titleAppearance, titleAppearance))
        setLogoIcon(ta.getResourceId(R.styleable.Thrall_logoIcon, logoIcon))
        setLogoSize(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoWidth, logoSize[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoHeight, logoSize[1])))
        setLogoPadding(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoPaddingLeft, logoPadding[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoPaddingRight, logoPadding[1]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoPaddingTop, logoPadding[2]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoPaddingBottom, logoPadding[3])))
        setLogoMargin(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoMarginLeft, logoMargin[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoMarginRight, logoMargin[1]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoMarginTop, logoMargin[2]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_logoMarginBottom, logoMargin[3])))
        setLogoVisible(ta.getBoolean(R.styleable.Thrall_logoVisible, logoVisible))
        val title = ta.getString(R.styleable.Thrall_title)
        if (title != null) {
            setTitle(title)
        }
        setTitleGravity(ta.getInt(R.styleable.Thrall_titleGravity, titleGravity))
        setTitleVisible(ta.getBoolean(R.styleable.Thrall_titleVisible, titleVisible))
        setTitleMargin(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_titleMarginLeft, titleMargin[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_titleMarginRight, titleMargin[1]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_titleMarginTop, titleMargin[2]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_titleMarginBottom, titleMargin[3])))
        setNavigationIcon(ta.getResourceId(R.styleable.Thrall_navigationIcon, navigationIcon))
        setNavigationSize(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationWidth, navigationSize[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationHeight, navigationSize[1])))
        setNavigationPadding(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationPaddingLeft, navigationPadding[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationPaddingRight, navigationPadding[1]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationPaddingTop, navigationPadding[2]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationPaddingBottom, navigationPadding[3])))
        setNavigationMargin(intArrayOf(
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationMarginLeft, navigationMargin[0]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationMarginRight, navigationMargin[1]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationMarginTop, navigationMargin[2]),
                ta.getDimensionPixelOffset(R.styleable.Thrall_navigationMarginBottom, navigationMargin[3])))
        setNavigationVisible(ta.getBoolean(R.styleable.Thrall_navigationVisible, navigationVisible))
        setOverflowIcon(ta.getResourceId(R.styleable.Thrall_overflowIcon, overflowIcon))
        val menuResId = ta.getResourceId(R.styleable.Thrall_menuResIds, 0)
        if (menuResId > 0) {
            try {
                val menuResIdArray = context.resources.getIntArray(menuResId)
                (0 until menuResIdArray.size)
                        .map { menuResIdArray[it] }
                        .forEach { addMenuResId(it) }
            } catch (e: Exception) {
                addMenuResId(menuResId)
            }
        }
        ta.recycle()
        return this
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

    fun setFitSystem(boolean: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            fitSystem = boolean
        }
        return this
    }

    fun setAsActionBar(boolean: Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            asActionBar = boolean
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

    /**
     * @param elevation Android sourcecode says "elevation" is a value in pixel though it's a Float type.
     */
    fun setElevation(elevation: Float): ThrallConfig {
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

    fun setLogoOnClickListener(listener: (View) -> Unit): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            logoOnClickListener = listener
        }
        return this
    }

    /**
     * @param size Size is an IntArray contains at least TWO Integers in pixels,
     * and the first is width and the second is height.
     */
    fun setLogoSize(@Px size: IntArray): ThrallConfig {
        if (size.size < 2) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            logoSize = size
        }
        return this
    }

    /**
     * @param padding Padding is an IntArray contains at least FOUR Integers in pixels,
     * and they are sorted as Left, Top, Right, Bottom.
     */
    fun setLogoPadding(@Px padding: IntArray): ThrallConfig {
        if (padding.size < 4) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            logoPadding = padding
        }
        return this
    }

    /**
     * @param margin Margin is an IntArray contains at least FOUR Integers in pixels,
     * and they are sorted as Left, Top, Right, Bottom.
     */
    fun setLogoMargin(@Px margin: IntArray): ThrallConfig {
        if (margin.size < 4) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            logoMargin = margin
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

    /**
     * @param margin Margin is an IntArray contains at least FOUR Integers in pixels,
     * and they are sorted as Left, Top, Right, Bottom.
     */
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

    /**
     * @param size Size is an IntArray contains at least TWO Integers in pixels,
     * and the first is width and the second is height.
     */
    fun setNavigationSize(@Px size: IntArray): ThrallConfig {
        if (size.size < 2) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            navigationSize = size
        }
        return this
    }

    /**
     * @param padding Padding is an IntArray contains at least FOUR Integers in pixels,
     * and they are sorted as Left, Top, Right, Bottom.
     */
    fun setNavigationPadding(@Px padding: IntArray): ThrallConfig {
        if (padding.size < 4) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            navigationPadding = padding
        }
        return this
    }

    /**
     * @param margin Margin is an IntArray contains at least FOUR Integers in pixels,
     * and they are sorted as Left, Top, Right, Bottom.
     */
    fun setNavigationMargin(@Px margin: IntArray): ThrallConfig {
        if (margin.size < 4) {
            //TODO throw ThrallException
        }
        if (!checkSavedDefaultLock()) {
            navigationMargin = margin
        }
        return this
    }

    fun setNavigationOnClickListener(listener: (View) -> Unit): ThrallConfig {
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

    fun setMenuOnItemClickListener(listener: (MenuItem) -> Boolean): ThrallConfig {
        if (!checkSavedDefaultLock()) {
            this.menuOnItemClickListener = listener
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

        private const val UNSETTLED_INT = ThrallConfig.UNSETTLED_INT
        private const val UNSETTLED_FLOAT = ThrallConfig.UNSETTLED_FLOAT
        private const val UNSETTLED_STRING = ThrallConfig.UNSETTLED_STRING

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
            setArrayWithDefault(config.titleMargin, 0)
            setArrayWithDefault(config.logoSize, 0)
            setArrayWithDefault(config.logoPadding, 0)
            setArrayWithDefault(config.logoMargin, 0)
            setArrayWithDefault(config.navigationSize, 0)
            setArrayWithDefault(config.navigationMargin, 0)
            setArrayWithDefault(config.navigationPadding, 0)
        }

        private fun setArrayWithDefault(array: IntArray, default: Int) {
            (0 until array.size)
                    .filter { isUnsettled(array[it]) }
                    .forEach { array[it] = default }
        }

        private fun isUnsettled(int: Int): Boolean = int == UNSETTLED_INT

        private fun isUnsettled(str: String): Boolean = str == UNSETTLED_STRING

        private fun isUnsettled(float: Float): Boolean = float == UNSETTLED_FLOAT

        private fun isUnsettled(intArray: IntArray): Boolean = (0 until intArray.size).any { isUnsettled(intArray[it]) }
    }

    internal companion object {

        internal const val UNSETTLED_INT = -13
        internal const val UNSETTLED_FLOAT = -13f
        internal const val UNSETTLED_STRING = "unsettled"

        private object Inner {
            val instance = ThrallConfig()
        }

        @JvmStatic internal fun default(): ThrallConfig = Inner.instance
    }

    internal fun cloneInstance(): ThrallConfig {
        val result = ThrallConfig()
        result.height = this.height
        result.paddingLeft = this.paddingLeft
        result.paddingRight = this.paddingRight
        result.backgroundColor = this.backgroundColor
        result.fitSystem = this.fitSystem
        result.asActionBar = this.asActionBar
        result.isInViewPager = this.isInViewPager
        result.visible = this.visible
        result.elevation = this.elevation
        result.shadowVisible = this.shadowVisible
        result.scrollBehaviorEnabled = this.scrollBehaviorEnabled
        result.toolbarTheme = this.toolbarTheme
        result.popupTheme = this.popupTheme
        result.titleAppearance = this.titleAppearance
        result.logoIcon = this.logoIcon
        result.logoSize = this.logoSize
        result.logoPadding = this.logoPadding
        result.logoMargin = this.logoMargin
        result.logoVisible = this.logoVisible
        result.logoOnClickListener = this.logoOnClickListener
        result.title = this.title
        result.titleGravity = this.titleGravity
        result.titleVisible = this.titleVisible
        result.titleMargin = this.titleMargin
        result.navigationIcon = this.navigationIcon
        result.navigationVisible = this.navigationVisible
        result.navigationSize = this.navigationSize
        result.navigationPadding = this.navigationPadding
        result.navigationMargin = this.navigationMargin
        result.navigationOnClickListener = this.navigationOnClickListener
        result.overflowIcon = this.overflowIcon
        result.menuResIds = arrayListOf()
        result.menuResIds.addAll(this.menuResIds)
        result.menuOnItemClickListener = this.menuOnItemClickListener
        result.customView = this.customView
        result.customViewLayoutParams = this.customViewLayoutParams
        return result
    }

    private fun checkSavedDefaultLock(): Boolean = isSaved && this === Inner.instance

}