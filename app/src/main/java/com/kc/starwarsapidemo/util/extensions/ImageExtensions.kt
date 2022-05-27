package com.kc.starwarsapidemo.util.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


/**
 * Load image using Glide
 * */
fun ImageView.loadImage(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .transform(CenterCrop())
        .placeholder(circularProgressDrawable)
        .into(this)
}

fun ImageView.loadImageDefault(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .into(this)
}


/**
 * Load image with a vignette
 */
@SuppressLint("CheckResult")
fun ImageView.loadVignetteImage(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    val options = RequestOptions()
//    options.transform(
//        GlideCombinedTransformation(
//            VignetteFilterTransformation(
//                PointF(0.5f, 0.5f),
//                floatArrayOf(0.0f, 0.0f, 0.0f), 0.27f, 0.97f
//            ), CenterCrop()
//        )
//    )

    Glide.with(context)
        .load(url)
        .apply(options)
        .placeholder(circularProgressDrawable)
        .into(this)
}

/**
 * Load image with radius
 */
fun ImageView.loadImageRound(url: String, radius: Int) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(radius))
        .placeholder(circularProgressDrawable)
        .into(this)
}

fun ImageView.loadImageCircleCrop(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(circularProgressDrawable)
        .into(this)
}

fun ImageView.loadImageCircleCrop(drawable: Drawable) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(drawable)
        .circleCrop()
        .placeholder(circularProgressDrawable)
        .into(this)
}

//fun ImageView.loadUserPlaceHolderImage() {
//    loadImageCircleCrop(
//        ContextCompat.getDrawable(
//            context,
//            R.drawable.bg_user_placeholder
//        )!!
//    )
//}


fun ImageView.loadImageRound(id: Int, radius: Int) {
    val circularProgressDrawable = CircularProgressDrawable(context)

    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 5f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(id)
        .transform(CenterCrop(), RoundedCorners(radius))
        .placeholder(circularProgressDrawable)
        .into(this)
}


/**
 * Load Compound Drawables
 */

fun ImageButton.loadImage(id: Int) {
    val image = ContextCompat.getDrawable(context, id)

}
