package com.kc.starwarsapidemo.util.base

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kc.starwarsapidemo.R
import com.kc.starwarsapidemo.util.extensions.hideKeyboard
import com.kc.starwarsapidemo.util.extensions.showToastLong

abstract class BaseActivity : AppCompatActivity() {
    private var progress: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = Dialog(this, R.style.ProgressbarStyle).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
//            setContentView(R.layout.item_progress)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor =
//            ContextCompat.getColor(
//                this,
//                R.color.colorAccent
//            )
    }

    fun setStatusBarColor(color: Int) {
        window.statusBarColor =
            ContextCompat.getColor(
                this,
                color
            )
    }

    fun showProgress() {
        progress?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideProgress() {
        progress?.let {
            if (it.isShowing) {
                progress?.dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        checkNetwork()
    }

    fun dismissKeyboard(view: View) {
        hideKeyboard()
    }

    fun checkNetwork() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        //take action when network connection is gained
                    }

                    override fun onLost(network: Network) {
                        showToastLong(getString(R.string.msg_offline))
                    }
                })
            } else {
                if (it.activeNetwork == null || !it.activeNetworkInfo!!.isConnected) {
                    showToastLong(getString(R.string.msg_offline))
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}