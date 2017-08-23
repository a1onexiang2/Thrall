package com.neilzheng.thrall

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.v4.app.Fragment as SupportFragment
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.style.TextAppearanceSpan
import android.view.View
import android.view.ViewGroup
import android.text.Spanned
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView


/**
 * Created by Neil Zheng on 2017/8/16.
 */
class Thrall private constructor() {

    companion object {

        private lateinit var toolbar: ThrallToolbar

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
            if (config.asSupportActionBar) {
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
            if (config.asSupportActionBar) {
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

        @JvmStatic fun onPrepareOptionsMenu(fragment: SupportFragment, menu: Menu) {
            val toolbar = ThrallUtils.findView(ThrallUtils.getRootView(fragment.activity),
                    ThrallToolbar::class.java) ?: return
            toolbar.updateMenuStyle(menu.size())
        }

        @JvmStatic fun onPrepareOptionsMenu(fragment: Fragment, menu: Menu) {
            val toolbar = ThrallUtils.findView(ThrallUtils.getRootView(fragment.activity),
                    ThrallToolbar::class.java) ?: return
            toolbar.updateMenuStyle(menu.size())
        }

        @JvmStatic fun onPrepareOptionsMenu(activity: Activity, menu: Menu) {
            val toolbar = ThrallUtils.findView(ThrallUtils.getRootView(activity), ThrallToolbar::class.java) ?: return
            toolbar.updateMenuStyle(menu.size())
        }

        private fun doBind(activity: Activity, rootView: ViewGroup, config: ThrallConfig) {
            if (!config.isInViewPager
                    && ThrallUtils.hasView(ThrallUtils.getRootView(activity), ThrallToolbar::class.java)) {
                //TODO multi Toolbar Exception
                throw ThrallException()
            }
            ThrallConfig.Checker.check(activity, config)

            val container = View.inflate(activity, R.layout.layout_container, null) as CoordinatorLayout
            val appBarContainer = container.findViewById(R.id.layout_appBar) as AppBarLayout
            toolbar = View.inflate(activity, R.layout.toolbar, null) as ThrallToolbar
            container.fitsSystemWindows = !config.isInViewPager || config.asSupportActionBar
            toolbar.setConfig(config)
            val toolbarParams = AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT,
                    AppBarLayout.LayoutParams.WRAP_CONTENT)
            toolbarParams.scrollFlags = if (config.scrollBehaviorEnabled)
                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL else 0
            toolbarParams.height = config.height
            appBarContainer.addView(toolbar, toolbarParams)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ThrallUtils.setElevation(appBarContainer, config.elevation)
            } else {
                ViewCompat.setElevation(appBarContainer, config.elevation)
            }

            ThrallUtils.addToRootView(rootView, container)

            if (config.asSupportActionBar && activity is AppCompatActivity) {
                activity.setSupportActionBar(toolbar)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(config.navigationVisible)
                activity.supportActionBar!!.setDisplayUseLogoEnabled(config.logoVisible)
                //TODO Customize the MenuItem
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