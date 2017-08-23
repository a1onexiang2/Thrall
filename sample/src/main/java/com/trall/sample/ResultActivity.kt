package com.trall.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.neilzheng.thrall.Thrall
import com.neilzheng.thrall.ThrallConfig
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

/**
 * Created by Neil Zheng on 2017/8/17.
 */
class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        initRecyclerView()
        initIntent()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this@ResultActivity)
        recyclerView.adapter = object : RecyclerView.Adapter<TextHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TextHolder {
                return TextHolder(View.inflate(this@ResultActivity, R.layout.item_text, null))
            }

            override fun onBindViewHolder(holder: TextHolder?, position: Int) {
                if (holder is TextHolder) {
                    holder.text.text = "我只是测试$position"
                }
            }

            override fun getItemCount(): Int = 100
        }
    }

    private fun initIntent() {
        val config = Thrall.newConfig()
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey("height")) {
                val str = extras.getString("height")
                try {
                    val int = str.toInt()
                    config.setHeight(int)
                } catch (ignored: Exception) {
                }
            }
            if (extras.containsKey("backgroundColor")) {
                val str = extras.getString("backgroundColor")
                try {
                    val int = str.toInt(16)
                    config.setBackgroundColor(int)
                } catch (ignored: Exception) {
                }
            }
            if (extras.containsKey("visible")) {
                config.setVisible(extras.getString("visible").toBoolean())
            }
            if (extras.containsKey("elevation")) {
                val str = extras.getString("elevation")
                try {
                    val float = str.toFloat()
                    config.setElevation(float)
                } catch (ignored: Exception) {
                }
            }
            if (extras.containsKey("shadowVisible")) {
                config.setVisible(extras.getString("shadowVisible").toBoolean())
            }
            if (extras.containsKey("scrollBehaviorEnabled")) {
                config.setScrollBehaviorEnabled(extras.getString("scrollBehaviorEnabled").toBoolean())
            }
            if (extras.containsKey("toolbarTheme")) {
                val value = extras.getString("toolbarTheme")
                when (value.toLowerCase(Locale.getDefault())) {
                    "light" -> config.setToolbarTheme(ThrallConfig.Theme.LIGHT())
                    "dark" -> config.setToolbarTheme(ThrallConfig.Theme.DARK())
                    "daynight" -> config.setToolbarTheme(ThrallConfig.Theme.DAY_NIGHT())
                }
            }
            if (extras.containsKey("popupTheme")) {
                val value = extras.getString("popupTheme")
                when (value.toLowerCase(Locale.getDefault())) {
                    "light" -> config.setPopupTheme(ThrallConfig.Theme.LIGHT())
                    "dark" -> config.setPopupTheme(ThrallConfig.Theme.DARK())
                    "daynight" -> config.setPopupTheme(ThrallConfig.Theme.DAY_NIGHT())
                }
            }
            if (extras.containsKey("titleAppearance")) {
                val value = extras.getString("titleAppearance")
                when (value.toLowerCase(Locale.getDefault())) {
                    "large" -> config.setTitleAppearance(android.R.style.TextAppearance_DeviceDefault_Large)
                    "medium" -> config.setTitleAppearance(android.R.style.TextAppearance_DeviceDefault_Medium)
                    "small" -> config.setTitleAppearance(android.R.style.TextAppearance_DeviceDefault_Small)
                }
            }
            if (extras.containsKey("logoIcon")) {
                val value = extras.getString("logoIcon")
                when (value.toLowerCase(Locale.getDefault())) {
                    "round" -> config.setLogoIcon(R.mipmap.ic_launcher_round)
                }
            }
            if (extras.containsKey("logoVisible")) {
                config.setLogoVisible(extras.getString("logoVisible").toBoolean())
            }
            if (extras.containsKey("titleGravity")) {
            }
            if (extras.containsKey("titleVisible")) {
                config.setTitleVisible(extras.getString("titleVisible").toBoolean())
            }
            if (extras.containsKey("navigationIcon")) {
                val value = extras.getString("navigationIcon")
                when (value.toLowerCase(Locale.getDefault())) {
                    "logo" -> config.setNavigationIcon(R.mipmap.ic_launcher)
                    "round" -> config.setNavigationIcon(R.mipmap.ic_launcher_round)
                }
            }
            if (extras.containsKey("navigationVisible")) {
                config.setNavigationVisible(extras.getString("navigationVisible").toBoolean())
            }
            if (extras.containsKey("overflowIcon")) {
                val value = extras.getString("overflowIcon")
                when (value.toLowerCase(Locale.getDefault())) {
                    "logo" -> config.setOverflowIcon(R.mipmap.ic_launcher)
                    "round" -> config.setOverflowIcon(R.mipmap.ic_launcher_round)
                }
            }
            if (extras.containsKey("menuPadding")) {
            }
            if (extras.containsKey("menuMargin")) {
            }
            if (extras.containsKey("menuMinWidth")) {
            }
            if (extras.containsKey("menuMaxWidth")) {
            }
        }
        Thrall.bind(this, config)
    }

    inner class TextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var text = itemView.findViewById(R.id.text) as TextView

    }
}