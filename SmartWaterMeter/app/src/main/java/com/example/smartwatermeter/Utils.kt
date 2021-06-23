package com.example.smartwatermeter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun doneAlert(fragment: Fragment, message: String, func: (() -> Unit)?) {
    MaterialAlertDialogBuilder(fragment.requireContext(), R.style.MaterialAlertDialog_color)
        .setTitle("Success")
        .setMessage(message)
        .setPositiveButton("Ok") { dialog, which ->
            if (func != null) {
                func()
            }
        }
        .setIcon(R.drawable.ic_check)
        .show()
}

fun hideKeyboard(activity: Activity) {
    val inputManager: InputMethodManager = activity
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    // check if no view has focus:
    val currentFocusedView: View? = activity.currentFocus
    if (currentFocusedView != null) {
        inputManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken,
            HIDE_NOT_ALWAYS
        )
    }
}