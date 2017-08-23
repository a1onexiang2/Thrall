package com.trall.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
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
                .addMenuResId(R.menu.main)
                .setMenuOnItemClickListener(Toolbar.OnMenuItemClickListener {
                    true })
                .setIsInViewPager(true)
                .setLogoIcon(R.mipmap.ic_launcher_round)
                .setShadowVisible(true)
                .bind(this)
    }
}