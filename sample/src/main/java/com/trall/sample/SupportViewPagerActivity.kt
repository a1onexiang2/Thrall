package com.trall.sample

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.neilzheng.thrall.Thrall
import kotlinx.android.synthetic.main.activity_viewpager.*

/**
 * Created by Neil Zheng on 2017/8/21.
 */
class SupportViewPagerActivity : AppCompatActivity() {

    private val fragments = arrayListOf<Fragment>(SimpleFragment(), SimpleFragment())
    private val titles = arrayListOf<String>("追剧", "离线")
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        initData()
        Thrall.newConfig()
                .setCustomView(tabLayout)
                .setNavigationVisible(true)
                .bind(this)
    }

    private fun initData() {
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = fragments[position]
            override fun getCount(): Int = fragments.size
            override fun getPageTitle(position: Int): CharSequence = titles[position]
        }
        tabLayout = TabLayout(this@SupportViewPagerActivity)
        tabLayout.setupWithViewPager(viewPager)
    }

}