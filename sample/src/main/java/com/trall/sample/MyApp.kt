package com.trall.sample

import android.app.Application
import android.graphics.Color
import android.view.Gravity
import com.neilzheng.thrall.Thrall
import com.neilzheng.thrall.ThrallConfig

/**
 * Created by Neil Zheng on 2017/8/17.
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initThrall()
    }

    private fun initThrall() {
        Thrall.defaultConfig()
                .setBackgroundColor(Color.WHITE)
                .setShadowVisible(true)
                .setNavigationIcon(R.drawable.icon_back)
                .setTitleGravity(Gravity.CENTER)
                .setTitleAppearance(R.style.Title)
                .setToolbarTheme(ThrallConfig.Theme.LIGHT())
                .setPopupTheme(ThrallConfig.Theme.LIGHT())
                .setMenuTextAppearance(R.style.Menu)
                .setMenuMinWidth(resources.getDimensionPixelOffset(R.dimen.menu_min_width))
                .setMenuMaxWidth(resources.getDimensionPixelOffset(R.dimen.menu_max_width))
                .setMenuHeightRatio(1f)
                .saveDefault()
    }
}