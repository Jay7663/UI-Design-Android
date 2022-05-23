package com.example.uidesignandroid.utils

import android.app.Activity
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.uidesignandroid.R


fun showAlert(context: Activity, title: String, message: String) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder
        .setTitle(title)
        .setMessage(message)
        .setCancelable(true).setIcon(R.drawable.icon_alert)
        .setPositiveButton(context.getString(R.string.dismiss)) { dialog, _ -> dialog.dismiss() }
        .show()
}

fun EditText.passwordVisibility(isHidden: Boolean) {
    this.transformationMethod =
        if (isHidden) PasswordTransformationMethod() else HideReturnsTransformationMethod()
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