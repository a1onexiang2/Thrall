package com.trall.sample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.neilzheng.thrall.Thrall
import kotlinx.android.synthetic.main.activity_result.*

/**
 * Created by Neil Zheng on 2017/8/18.
 */
class ResultSupportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        Thrall.newConfig()
                .setTitle("ResultSupportFragment")
                .setTitleGravity(Gravity.BOTTOM or Gravity.LEFT)
                .setLogoVisible(true)
                .setScrollBehaviorEnabled(true)
                .setLogoIcon(R.mipmap.ic_launcher_round)
                .setShadowVisible(true)
                .bind(this)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = object : RecyclerView.Adapter<TextHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TextHolder {
                return TextHolder(View.inflate(activity, R.layout.item_text, null))
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