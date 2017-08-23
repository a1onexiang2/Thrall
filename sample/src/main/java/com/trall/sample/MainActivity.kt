package com.trall.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.neilzheng.thrall.Thrall
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val settingArray = arrayOf("height",
            "backgroundColor",
            "visible",
            "elevation",
            "shadowVisible",
            "scrollBehaviorEnabled",
            "toolbarTheme",
            "popupTheme",
            "titleAppearance",
            "logoIcon",
            "logoVisible",
            "titleGravity",
            "titleVisible",
            "navigationIcon",
            "navigationVisible",
            "overflowIcon",
            "menuPadding",
            "menuMargin",
            "menuMinWidth",
            "menuMaxWidth")

    private val typeArray = arrayOf("(int in px)",
            "(int like ff0000)",
            "(true or anything)",
            "(int in px)",
            "(true or anything)",
            "(true or anything)",
            "(light, dark or daynight)",
            "(light, dark or daynight)",
            "(large, medium or small)",
            "(round or anything)",
            "(true or anything)",
            "(TODO)",
            "(true or anything)",
            "(round, logo or anything)",
            "(true or anything)",
            "(round, logo or anything)",
            "(TODO)",
            "(TODO)",
            "(TODO)",
            "(TODO)")
    private lateinit var adapter: SettingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thrall.newConfig()
                .setTitle("Thrall Sample")
                .setLogoIcon(R.mipmap.ic_launcher_round)
                .setLogoVisible(true)
                .setAsActionBar(true)
                .bind(this)
        initRecyclerView()
        initListener()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        adapter = SettingAdapter()
        recyclerView.adapter = adapter
    }

    private fun initListener() {
        button_activity.setOnClickListener {
            startActivity(generateIntent(ResultActivity::class.java))
        }
        button_fragment.setOnClickListener {
            startActivity(generateIntent(ResultFragmentActivity::class.java))
        }
        button_support_fragment.setOnClickListener {
            startActivity(generateIntent(ResultSupportFragmentActivity::class.java))
        }
        button_viewPager.setOnClickListener {
            startActivity(generateIntent(SupportViewPagerActivity::class.java))
        }
    }

    private fun <T> generateIntent(clazz: Class<T>): Intent {
        val result = Intent(this@MainActivity, clazz)
        for (i in 0..adapter.itemCount - 1) {
            val key = settingArray[i]
            val text: String = adapter.getItemValue(i)
            if (text.isNotEmpty()) {
                result.putExtra(key, text)
            }
        }
        return result
    }

    inner class SettingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val edits = hashMapOf<Int, TextInputEditText>()
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            return SettingHolder(View.inflate(this@MainActivity, R.layout.item_edit, null))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if (holder is SettingHolder) {
                holder.textInputLayout.hint = settingArray[position] + typeArray[position]
                edits.put(position, holder.textInputEditText)
            }
        }

        fun getItemValue(position: Int): String = edits[position]?.text.toString()

        override fun getItemCount(): Int = settingArray.size
    }

    inner class SettingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textInputEditText = itemView.findViewById(R.id.textInputEditText) as TextInputEditText
        internal var textInputLayout = itemView.findViewById(R.id.textInputLayout) as TextInputLayout

    }
}