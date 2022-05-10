package com.example.uidesignandroid.activities.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.uidesignandroid.R
import com.example.uidesignandroid.databinding.ActivitySignInBinding
import com.example.uidesignandroid.utils.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var isHiddenPassword = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            if (validateInputs()) {
                Toast.makeText(this, "Success...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.customToolbar.arrowImageView.setOnClickListener {
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordOptionsActivity::class.java))
        }

        binding.etEmail.isSelected = true
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            binding.etEmail.isSelected = text.toString().trim().isEmpty()
        }

        binding.etPassword.isSelected = true
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            binding.etPassword.isSelected = text.toString().trim().isEmpty()
        }

        putSpannableStringInTV()
        setUpToggle()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpToggle() {
        binding.etPassword.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val right = 2
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        if (event.rawX >= binding.etPassword.right - binding.etPassword.compoundDrawables[right].bounds.width() - 70) {
                            val selection = binding.etPassword.selectionEnd
                            binding.etPassword.passwordVisibility(isHiddenPassword)
                            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.custom_lock,
                                0,
                                if (isHiddenPassword) R.drawable.custom_eye_hidden else R.drawable.custom_eye_show,
                                0
                            )
                            isHiddenPassword = !isHiddenPassword
                            binding.etPassword.setSelection(selection)
                        }
                    }
                }
            }
            view?.onTouchEvent(event) ?: true
        }
    }

    private fun putSpannableStringInTV() {
        val spannableString =
            addSpannableString(this, getString(R.string.don_t_have_an_account), 23, 30) {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        binding.tvSignUp.text = spannableString
        binding.tvSignUp.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun validateInputs(): Boolean {
        return if (checkForNull(binding.etEmail, binding.etPassword)) {
            false
        } else if (!isValidEmail(binding.etEmail)) {
            false
        } else isValidPassword(binding.etPassword)
    }

}