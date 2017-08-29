package com.neilzheng.thrall

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.v4.app.Fragment as SupportFragment
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuItem


/**
 * Created by Neil Zheng on 2017/8/16.
 */
class Thrall private constructor() {

    companion object {

        @JvmStatic fun bind(fragment: Fragment) {
            bind(fragment, ThrallConfig.default())
        }

        @JvmStatic fun bind(fragment: SupportFragment) {
            bind(fragment, ThrallConfig.default())
        }

        @JvmStatic fun bind(activity: AppCompatActivity) {
            bind(activity, ThrallConfig.default())
        }

        @JvmStatic fun bind(fragment: Fragment, config: ThrallConfig) {
            if (config.asActionBar) {
                val activity = fragment.activity as? AppCompatActivity ?: //TODO not AppCompactActivity Exception
                        throw ThrallException()
                doBind(activity, ThrallUtils.getRootView(activity), config)
            } else if (config.isInViewPager) {
                val rootView = fragment.view as? ViewGroup ?: throw ThrallException()
                doBind(fragment.activity, rootView, config)
            } else {
                doBind(fragment.activity, fragment.view.parent as ViewGroup, config)
            }
        }

        @JvmStatic fun bind(fragment: SupportFragment, config: ThrallConfig) {
            if (config.asActionBar) {
                val activity = fragment.activity as? AppCompatActivity ?: //TODO not AppCompactActivity Exception
                        throw ThrallException()
                doBind(activity, ThrallUtils.getRootView(activity), config)
            } else if (config.isInViewPager) {
                val rootView = fragment.view as? ViewGroup ?: throw ThrallException()
                doBind(fragment.activity, rootView, config)
            } else {
                doBind(fragment.activity, fragment.view!!.parent as ViewGroup, config)
            }
        }

        @JvmStatic fun bind(activity: AppCompatActivity, config: ThrallConfig) {
            doBind(activity, ThrallUtils.getRootView(activity), config)
        }

        @JvmStatic fun defaultConfig(): ThrallConfig {
            return ThrallConfig.default()
        }

        @JvmStatic fun newConfig(): ThrallConfig {
            return ThrallConfig.default().cloneInstance()
        }

        @JvmStatic fun totallyNewConfig(): ThrallConfig {
            return ThrallConfig()
        }

        @JvmStatic fun findToolbar(fragment: SupportFragment): ThrallToolbar? {
            return ThrallUtils.findView(ThrallUtils.getRootView(fragment.activity), ThrallToolbar::class.java)
        }

        @JvmStatic fun findToolbar(fragment: Fragment): ThrallToolbar? {
            return ThrallUtils.findView(ThrallUtils.getRootView(fragment.activity), ThrallToolbar::class.java)
        }

        @JvmStatic fun findToolbar(activity: Activity): ThrallToolbar? {
            return ThrallUtils.findView(ThrallUtils.getRootView(activity), ThrallToolbar::class.java)
        }

        @JvmStatic fun findMenuItem(fragment: Fragment, @IdRes id: Int): MenuItem? {
            return ThrallUtils.findView(fragment.view as ViewGroup, ThrallToolbar::class.java)
                    ?.menu?.findItem(id)
        }

        @JvmStatic fun findMenuItem(fragment: SupportFragment, @IdRes id: Int): MenuItem? {
            return ThrallUtils.findView(fragment.view as ViewGroup, ThrallToolbar::class.java)
                    ?.menu?.findItem(id)
        }

        @JvmStatic fun findMenuItem(activity: Activity, @IdRes id: Int): MenuItem? {
            return ThrallUtils.findView(ThrallUtils.getRootView(activity), ThrallToolbar::class.java)?.menu?.findItem(id)
        }

        private fun doBind(activity: Activity, rootView: ViewGroup, config: ThrallConfig) {
            if (!config.isInViewPager
                    && ThrallUtils.hasView(ThrallUtils.getRootView(activity), ThrallToolbar::class.java)) {
                //TODO multi Toolbar Exception
                throw ThrallException()
            }
            ThrallConfig.Checker.check(config)

            val container = View.inflate(activity, R.layout.layout_container, null) as CoordinatorLayout
            val appBarContainer = container.findViewById(R.id.layout_appBar) as AppBarLayout
            val toolbar = View.inflate(activity, R.layout.toolbar, null) as ThrallToolbar
            container.fitsSystemWindows = config.fitSystem
            val toolbarParams = AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT,
                    AppBarLayout.LayoutParams.WRAP_CONTENT)
            appBarContainer.addView(toolbar, toolbarParams)
            toolbar.setConfig(config)

            val elevation = if (config.shadowVisible) config.elevation else 0f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ThrallUtils.setElevation(appBarContainer, elevation)
            } else {
                ViewCompat.setElevation(appBarContainer, elevation)
            }

            ThrallUtils.addToRootView(rootView, container)

            if (config.asActionBar) {
                if (activity is AppCompatActivity) {
                    activity.setSupportActionBar(toolbar)
                    activity.supportActionBar!!.setDisplayHomeAsUpEnabled(config.navigationVisible)
                    activity.supportActionBar!!.setDisplayUseLogoEnabled(config.logoVisible)
                    //TODO Customize the MenuItem
                }
            } else {
                if (config.menuResIds.isNotEmpty()) {
                    config.menuResIds.forEach {
                        toolbar.inflateMenu(it)
                    }
                }
                toolbar.setOnMenuItemClickListener(config.menuOnItemClickListener)
            }

            /**
             * #Toolbar.setNavigationOnClickListener(#View.OnClickListener listener) and
             * #Toolbar.setOnMenuItemClickListener(#Toolbar.onMenuItemClickListener) won't work if called
             * before calling #Activity.setSupportActionBar(#Toolbar toolbar)
             */
            toolbar.setNavigationOnClickListener(config.navigationOnClickListener)
        }

    }

}