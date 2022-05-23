package com.example.uidesignandroid.base

import android.app.Activity
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.uidesignandroid.R

open class BaseActivity : AppCompatActivity() {

    fun checkForNull(vararg fields: EditText): Boolean {
        for (editText in fields) {
            if (editText.text.trim().isEmpty()) {
                editText.isFocusable = true
                editText.error = getString(R.string.validation_method_required_field)
                editText.requestFocus()
                return true
            }
        }
        return false
    }

    fun isValidEmail(editText: EditText): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(editText.text).matches()) {
            editText.isFocusable = true
            editText.error = getString(R.string.validation_message_incorrect_email)
            editText.requestFocus()
            return false
        }
        return true
    }

    fun isValidPassword(editText: EditText): Boolean {
        if (editText.text.length < 4) {
            editText.isFocusable = true
            editText.error = getString(R.string.validation_message_need_4_char_password)
            editText.requestFocus()
            return false
        }
        return true
    }

    fun hideSoftKeyboard() {
        currentFocus.let {
            val inputMethodManager: InputMethodManager =
                this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager.isAcceptingText) {
                inputMethodManager.hideSoftInputFromWindow(it?.windowToken, 0)
            }
        }
    }

}