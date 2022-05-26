package com.kc.starwarsapidemo.util.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import kotlin.math.roundToInt

fun Float.pxToDp(): Float {
    val densityDpi = Resources.getSystem()
        .displayMetrics.densityDpi.toFloat()
    return this / (densityDpi / 160f)
}

fun Int.dpToPx(): Float {
    val density = Resources.getSystem()
        .displayMetrics.density
    return this * density
}

fun Context.pxToDp(value: Float): Int {
    val r: Resources = resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value, r.displayMetrics
    ).roundToInt()
}

fun Float.dipsToPix(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics
)

fun WindowManager.getDeviceHeight(): Int {
    val display: Display = defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}

fun WindowManager.getDeviceWidth(): Int {
    val display: Display = defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

