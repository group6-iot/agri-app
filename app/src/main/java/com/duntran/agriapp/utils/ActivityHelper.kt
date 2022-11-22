package com.duntran.agriapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

@SuppressLint("ClickableViewAccessibility")
fun Activity.hideKeyboardOnClickOutside(view: View) {
    if (view !is EditText) {
        view.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            hideKeyboardOnClickOutside(child)
        }
    }
}

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}