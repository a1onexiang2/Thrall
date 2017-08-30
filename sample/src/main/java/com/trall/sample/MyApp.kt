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
                .setToolbarTheme(ThrallConfig.Theme.DAY_NIGHT())
                .setPopupTheme(ThrallConfig.Theme.LIGHT())
                .loadStyle(this, R.style.Toolbar)
                .saveDefault()
    }
}