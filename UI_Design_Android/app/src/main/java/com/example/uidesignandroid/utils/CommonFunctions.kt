package com.example.uidesignandroid.utils

import android.app.Activity
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.uidesignandroid.R


fun Activity.checkForNull(vararg fields: EditText): Boolean {
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

fun Activity.isValidEmail(editText: EditText): Boolean {
    if (!Patterns.EMAIL_ADDRESS.matcher(editText.text).matches()) {
        editText.isFocusable = true
        editText.error = getString(R.string.validation_message_incorrect_email)
        editText.requestFocus()
        return false
    }
    return true
}

fun Activity.isValidPassword(editText: EditText): Boolean {
    if (editText.text.length < 4) {
        editText.isFocusable = true
        editText.error = getString(R.string.validation_message_need_4_char_password)
        editText.requestFocus()
        return false
    }
    return true
}

fun addSpannableString(
    context: Activity,
    string: String,
    start: Int,
    end: Int,
    onCLickCallBack: () -> Unit
): SpannableString {

    val span: ClickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            onCLickCallBack()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(context, R.color.primary_500)
            ds.isUnderlineText = false
            ds.isFakeBoldText = true
        }
    }

    val spannableString = SpannableString(string)
    spannableString.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

fun EditText.passwordVisibility(isHidden: Boolean) {
    this.transformationMethod =
        if (isHidden) PasswordTransformationMethod() else HideReturnsTransformationMethod()
}

fun Activity.hideSoftKeyboard() {
    currentFocus.let {
        val inputMethodManager: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(it?.windowToken, 0)
        }
    }
}