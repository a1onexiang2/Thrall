package com.trall.sample

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import com.neilzheng.thrall.Thrall
import kotlinx.android.synthetic.main.activity_result.*

/**
 * Created by Neil Zheng on 2017/8/18.
 */
class ResultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.activity_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Thrall.onPrepareOptionsMenu(this, menu)
        super.onPrepareOptionsMenu(menu)
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

        internal var text = itemView.findViewById(R.id.text) as TextView

    }
}