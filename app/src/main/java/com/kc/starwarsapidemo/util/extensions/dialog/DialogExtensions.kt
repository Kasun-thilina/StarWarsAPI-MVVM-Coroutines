package com.kc.starwarsapidemo.util.extensions.dialog

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

const val TAG = "dialog"

inline fun Activity.alertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    func: DialogHelper.() -> Unit
) {
    val dialogFragment = DialogHelper(this, title, message).apply {
        func()
    }
    val fragmentTransaction = (this as AppCompatActivity).supportFragmentManager.beginTransaction()
    fragmentTransaction.let { dialogFragment.show(it, TAG) }
}

inline fun Fragment.alertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    func: DialogHelper.() -> Unit
) {
    val dialogFragment = DialogHelper(this.requireContext(), title, message).apply {
        func()
    }
    val fragmentTransaction = (this).childFragmentManager.beginTransaction()
    fragmentTransaction.let { dialogFragment.show(it, TAG) }
}

