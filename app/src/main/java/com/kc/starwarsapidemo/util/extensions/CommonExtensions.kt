package com.kc.starwarsapidemo.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import com.kc.starwarsapidemo.R
import okhttp3.internal.trimSubstring


/**
 * Show Long Toast message from string resource
 * */
fun Context?.showToastLong(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) {
    this?.let {
        Toast.makeText(it, textId, duration).show()
    }

}

/**
 * Show Long Toast message from only String
 * */
fun Context?.showToastLong(text: String, duration: Int = Toast.LENGTH_LONG) {
    this?.let {
        Toast.makeText(it, text, duration).show()
    }
}

/**
 * Start Activity from context
 * */
inline fun <reified T : Activity> Context?.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java).apply {
        func()
    }
    this?.startActivity(intent)
}

/**
 * Hide Keyboard
 */
fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    window.decorView
}

/**
 * Permission Check
 * */
fun Activity.isPermissionGranted(vararg permissions: String): Boolean {
    return permissions.all { permission ->
        applicationContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}

fun Activity.requestPermission(permissions: Array<String>, REQUEST_CODE: Int) {
    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
}

/**
 * Location Provider check
 * */

fun Activity.isProviderEnabled(): Boolean {
    val manager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

/**
 * Set Support Action Bar
 */

fun ActionBar.setActionBar(
    context: Context,
    title: String,
    isHomeUpEnabled: Boolean = false,
) {
    val tv = TextView(context.applicationContext)
    val lp = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    ) // Height of TextView
    tv.layoutParams = lp
    tv.text = title // ActionBar title text
    tv.setTextColor(Color.WHITE)
//    val face = ResourcesCompat.getFont(context, R.font.roboto)
//    tv.typeface = face

    tv.typeface
    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
    // Set the ActionBar display option
    this.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    this.customView = tv

    this.elevation = 1.dpToPx()
    if (isHomeUpEnabled) {
        this.setDisplayHomeAsUpEnabled(true)
        this.setDisplayShowHomeEnabled(true)
        this.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }
}


/**
 * Set Support Action Bar
 */

fun ActionBar.setActionBar(
    context: Context,
    isHomeUpEnabled: Boolean = false,
) {
    val tv = TextView(context.applicationContext)
    val lp = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    ) // Height of TextView
    tv.layoutParams = lp
    tv.text = "" // ActionBar title text
    tv.setTextColor(Color.WHITE)
//    val face = ResourcesCompat.getFont(context, R.font.roboto)
//    tv.typeface = face
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        // tv.setTextAppearance(R.style.TextWhiteBoltStyle_size17)
//    }
    tv.typeface
    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
    // Set the ActionBar display option
    this.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    this.customView = tv

    this.elevation = 1.dpToPx()
    if (isHomeUpEnabled) {
        this.setDisplayHomeAsUpEnabled(true)
        this.setDisplayShowHomeEnabled(true)
//        this.setHomeAsUpIndicator(R.drawable.ic_back_transparent_svg)
    }
}

/**
 * validate email
 */
fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()


/**
 * Text Trim
 */

fun String.trimIfLong(length: Int): String {
    return if (this.length > length) {
        "${this.trimSubstring(0, length)}..."
    } else {
        this
    }
}

fun TextView.scaleIfLong(length: Int) {
    if (this.text.length > length) {
        textSize = 14F
    }
}

/**
 * Two Way Paging
 */

fun <T> List<T>.prepareForTwoWayPaging(): List<T> {
    val first = first()
    val last = last()
    return toMutableList().apply {
        add(0, last)
        add(first)
    }
}

