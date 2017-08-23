package com.trall.sample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neilzheng.thrall.Thrall

/**
 * Created by Neil Zheng on 2017/8/21.
 */
class SimpleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_simple, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Thrall.newConfig()
                .setTitle("SimpleFragment")
                .setTitleGravity(Gravity.BOTTOM or Gravity.LEFT)
                .setLogoVisible(true)
                .setIsInViewPager(true)
                .setLogoIcon(R.mipmap.ic_launcher_round)
                .setShadowVisible(true)
                .bind(this)
    }
}