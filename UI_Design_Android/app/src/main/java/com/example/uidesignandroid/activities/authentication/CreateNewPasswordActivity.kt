package com.example.uidesignandroid.activities.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.example.uidesignandroid.R
import com.example.uidesignandroid.activities.homescreen.HomeActivity
import com.example.uidesignandroid.base.BaseActivity
import com.example.uidesignandroid.databinding.ActivityCreateNewPasswordBinding
import com.example.uidesignandroid.utils.Constants.SEVENTY
import com.example.uidesignandroid.utils.Constants.TWO
import com.example.uidesignandroid.utils.Constants.ZERO
import com.example.uidesignandroid.utils.passwordVisibility

class CreateNewPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateNewPasswordBinding
    private var builder: AlertDialog? = null
    private var isHiddenPassword = true
    private var isConfirmPasswordHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewPasswordBinding.inflate(layoutInflater)
        binding.customToolbar.tvTitle.text = getString(R.string.create_new_password)
        setContentView(binding.root)

        setUp()
        setUpToggle()
    }

    override fun onPause() {
        super.onPause()
        builder?.dismiss()
    }

    private fun setUp() {
        binding.etPassword.isSelected = true
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            binding.etPassword.isSelected = text.toString().trim().isEmpty()
        }

        binding.etConfirmPassword.isSelected = true
        binding.etConfirmPassword.doOnTextChanged { text, _, _, _ ->
            binding.etConfirmPassword.isSelected = text.toString().trim().isEmpty()
        }

        binding.btnContinue.setOnClickListener {
            if (!checkForNull(binding.etPassword, binding.etConfirmPassword) &&
                isValidPassword(binding.etPassword) &&
                isValidPassword(binding.etConfirmPassword)
            ) {
                builder =
                    AlertDialog.Builder(this@CreateNewPasswordActivity, R.style.CustomAlertDialog)
                        .create()
                val view = layoutInflater.inflate(R.layout.custom_alert, null)
                builder?.setView(view)
                val btnHomepage = view.findViewById<Button>(R.id.btnGoToHomepage)
                btnHomepage.setOnClickListener {
                    finish()
                    startActivity(Intent(this@CreateNewPasswordActivity, HomeActivity::class.java))
                }
                builder?.setCanceledOnTouchOutside(true)
                builder?.show()
            }
        }

        binding.customToolbar.arrowImageView.setOnClickListener {
            finish()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpToggle() {
        binding.etPassword.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        if (event.rawX >= binding.etPassword.right - binding.etPassword.compoundDrawables[TWO].bounds.width() - SEVENTY) {
                            binding.etPassword.passwordVisibility(isHiddenPassword)
                            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.custom_lock,
                                ZERO,
                                if (isHiddenPassword) R.drawable.custom_eye_hidden else R.drawable.custom_eye_show,
                                ZERO
                            )
                            isHiddenPassword = !isHiddenPassword
                        }
                    }
                }
            }
            view?.onTouchEvent(event) ?: true
        }

        binding.etConfirmPassword.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        if (event.rawX >= binding.etConfirmPassword.right - binding.etConfirmPassword.compoundDrawables[TWO].bounds.width() - SEVENTY) {
                            binding.etConfirmPassword.passwordVisibility(isConfirmPasswordHidden)
                            binding.etConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.custom_lock,
                                ZERO,
                                if (isConfirmPasswordHidden) R.drawable.custom_eye_hidden else R.drawable.custom_eye_show,
                                ZERO
                            )
                            isConfirmPasswordHidden = !isConfirmPasswordHidden
                        }
                    }
                }
            }
            view?.onTouchEvent(event) ?: true
        }
    }
}