package com.trall.sample

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.TextView
import com.neilzheng.thrall.Thrall
import kotlinx.android.synthetic.main.activity_result.*
import java.lang.reflect.Array.setBoolean
import java.lang.reflect.AccessibleObject.setAccessible
import android.view.LayoutInflater
import android.widget.Toast


class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        initRecyclerView()
        Thrall.newConfig()
                .setLogoIcon(R.mipmap.ic_launcher)
                .setLogoVisible(true)
                .setLogoOnClickListener {
                    Toast.makeText(this@TestActivity, "123", Toast.LENGTH_SHORT).show()
                }
                .setTitle("123123")
                .setTitleGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
                .bind(this)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this@TestActivity)
        recyclerView.adapter = object : RecyclerView.Adapter<TextHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TextHolder {
                return TextHolder(View.inflate(this@TestActivity, R.layout.item_text, null))
            }

            override fun onBindViewHolder(holder: TextHolder?, position: Int) {
                if (holder is TextHolder) {
                    holder.text.text = "我只是测试$position"
                }
            }

            override fun getItemCount(): Int = 100
        }
    }

    inner class TextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var text = itemView.findViewById<TextView>(R.id.text)

    }
}