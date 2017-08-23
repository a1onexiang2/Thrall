package com.trall.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Neil Zheng on 2017/8/18.
 */
class ResultSupportFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fragment)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame, ResultSupportFragment())
        transaction.commit()
    }

}