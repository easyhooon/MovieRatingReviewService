package kr.ac.konkuk.movieratingreviewservice.extension

import android.view.View
import androidx.annotation.Px
import kr.ac.konkuk.movieratingreviewservice.extension.dip

@Px
fun View.dip(dipValue: Float) = context.dip(dipValue)

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}