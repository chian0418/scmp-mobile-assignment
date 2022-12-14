package com.example.scmp_mobile_assignment.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Toast
import com.example.scmp_mobile_assignment.R
import java.io.IOException
import java.net.URL


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

fun URL.toBitmap(): Bitmap? {
    return try {
        BitmapFactory.decodeStream(openStream())
    } catch (e: IOException) {
        null
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
fun Activity.leaveFromRight ()= run { overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right) }

fun Activity.leaveFromLeft ()= run { overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left) }