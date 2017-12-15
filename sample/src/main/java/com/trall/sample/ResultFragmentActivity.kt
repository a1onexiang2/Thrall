package com.trall.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import com.neilzheng.thrall.Thrall


/**
 * Created by Neil Zheng on 2017/8/18.
 */
class ResultFragmentActivity : AppCompatActivity() {

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fragment)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.frame, ResultFragment())
        transaction.commit()
        Thrall.newConfig()
                .setTitle("ResultFragmentActivity")
                .setLogoIcon(R.mipmap.ic_launcher)
                .setLogoVisible(true)
                .setNavigationVisible(true)
                .setLogoSize(intArrayOf(72, 72))
                .setPaddingLeft(40)
                .setPaddingRight(40)
//                .setAsActionBar(true)
                .addMenuResId(R.menu.main)
                .addMenuResId(R.menu.fragment)
                .setMenuOnItemClickListener {
                    true
                }
                .setScrollBehaviorEnabled(true)
                .bind(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                isEditing = !isEditing
                item.titleCondensed = if (isEditing) "完成" else "编辑"
            }
        }
        return super.onOptionsItemSelected(item)
    }
}