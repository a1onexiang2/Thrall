package com.neilzheng.thrall

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.os.Build
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
* Created by Neil Zheng on 2017/8/17.
*/
class ThrallUtils {

    companion object {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        internal fun setElevation(view: View, elevation: Float) {
            val sla = StateListAnimator()
            sla.addState(intArrayOf(0), ObjectAnimator.ofFloat(view, "elevation", elevation).setDuration(1))
            view.stateListAnimator = sla
        }

        internal fun <T : View> findView(view: View, clazz: Class<T>): T? {
            if (clazz.isInstance(view)) {
                return view as T
            }
            when (view) {
                is ViewGroup -> (0 until view.childCount).forEach {
                    val result = findView(view.getChildAt(it), clazz)
                    if (null != result) {
                        return result
                    }
                }
            }
            return null
        }

        internal fun <T : View> findAllView(view: View, clazz: Class<T>): MutableList<T> {
            val result = arrayListOf<T>()
            if (clazz.isInstance(view)) {
                result.add(view as T)
            } else {
                when (view) {
                    is ViewGroup -> (0 until view.childCount).forEach {
                        result.addAll(findAllView(view.getChildAt(it), clazz))
                    }
                }
            }
            return result
        }

        internal fun <T : View> hasView(view: View, clazz: Class<T>): Boolean = findView(view, clazz) != null

        internal fun printView(view: View) {
            when (view) {
                is ViewGroup -> {
                    Log.e("printView",
                            "find ViewGroup : ${view.javaClass.name} @ ${view.id} with ${view.childCount} children")
                    (0 until view.childCount).forEach {
                        printView(view.getChildAt(it))
                    }
                }
                else -> {
                    Log.e("printView", "find a child View : ${view.javaClass.name} @ ${view.id}")
                }
            }
        }

        internal fun addToRootView(rootView: ViewGroup, container: ViewGroup) {
            val containerDelegate: ViewGroup
            when (rootView) {
                is LinearLayout -> {
                    containerDelegate = newInstance(rootView.context, LinearLayout::class.java)
                    containerDelegate.orientation = rootView.orientation
                }
                is RelativeLayout -> {
                    containerDelegate = newInstance(rootView.context, RelativeLayout::class.java)
                }
                else -> {
                    containerDelegate = newInstance(rootView.context, FrameLayout::class.java)
                }
            }
            for (i in rootView.childCount - 1 downTo 0) {
                val child = rootView.getChildAt(i)
                val childParams = child.layoutParams
                rootView.removeViewAt(i)
                containerDelegate.addView(child, 0, childParams)
            }
            val params = CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT)
            params.behavior = AppBarLayout.ScrollingViewBehavior()
            container.addView(containerDelegate, params)
            rootView.addView(container)
        }

        internal fun <T : ViewGroup> newInstance(context: Context, clazz: Class<T>): T =
                clazz.getConstructor(Context::class.java).newInstance(context)

        internal fun getRootView(activity: Activity): ViewGroup = activity.findViewById(android.R.id.content)

        internal fun getActivityFromView(view: View): Activity? {
            var context = view.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }

        internal fun dp2pxf(dp: Int): Float = dp * Resources.getSystem().displayMetrics.density

        @Px internal fun dp2px(dp: Int): Int = dp2pxf(dp).toInt()

        internal fun sp2pxf(sp: Int): Float = sp * Resources.getSystem().displayMetrics.scaledDensity + 0.5f

        @Px internal fun sp2px(sp: Int): Int = sp2pxf(sp).toInt()

    }

}