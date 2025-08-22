package com.example.core.presentation.util

import android.content.Context
import android.widget.Toast
import com.example.core.presentation.ui.UiText

fun Context.showToast(msg : UiText) {
    val msgAsString = msg.asString(this)
    Toast.makeText(this , msgAsString , Toast.LENGTH_SHORT).show()
}