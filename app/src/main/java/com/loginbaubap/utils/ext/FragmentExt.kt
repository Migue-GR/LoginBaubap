package com.loginbaubap.utils.ext

import android.widget.Toast
import androidx.fragment.app.Fragment
import timber.log.Timber

infix fun Fragment.showToast(message: String) = try {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
} catch (e: IllegalArgumentException) {
    Timber.e(e, "Error while trying to show toast")
}