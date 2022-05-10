package com.example.uidesignandroid.activities.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.uidesignandroid.R
import com.example.uidesignandroid.activities.homescreen.HomeActivity
import com.example.uidesignandroid.databinding.ActivitySignUpBinding
import com.example.uidesignandroid.interfaces.ApiCallsInterface
import com.example.uidesignandroid.models.SignUpUserResponse
import com.example.uidesignandroid.utils.addSpannableString
import com.example.uidesignandroid.utils.isValidEmail
import com.example.uidesignandroid.utils.passwordVisibility
import com.example.uidesignandroid.viewmodels.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.uidesignandroid.R
import com.example.uidesignandroid.databinding.ActivitySignUpBinding
import com.example.uidesignandroid.utils.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var isHiddenPassword = true
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setCustomView(R.layout.custom_toolbar_layout)

        binding.btnSignUp.setOnClickListener {
            if (validateInputs()) {
                binding.progressBar.visibility = View.VISIBLE
                lifecycleScope.launch(Dispatchers.IO) {
                    signUpViewModel.register(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString(),
                        SignUpUserResponse::class.java
                    )
                }
            }
        }

        binding.customToolbar.arrowImageView.setOnClickListener {
            finish()
        }

        binding.etEmail.isSelected = true
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            binding.etEmail.isSelected = text.toString().trim().isEmpty()
        }

        binding.etPassword.isSelected = true
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            binding.etPassword.isSelected = text.toString().trim().isEmpty()
        }

        tvSignInCreateSpannable()
        setUpToggle()
        setUpViewModelObservers()
    }

    private fun setUpViewModelObservers() {
        signUpViewModel.apply {
            isValidUser.observe(this@SignUpActivity) {
                if (it) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@SignUpActivity, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                    finish()
                }
            }
            errorMessage.observe(this@SignUpActivity) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@SignUpActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpToggle() {
        binding.etPassword.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val right = 2
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        if (event.rawX >= binding.etPassword.right - binding.etPassword.compoundDrawables[right].bounds.width() - 70) {
                            binding.etPassword.passwordVisibility(isHiddenPassword)
                            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.custom_lock,
                                0,
                                if (isHiddenPassword) R.drawable.custom_eye_hidden else R.drawable.custom_eye_show,
                                0
                            )
                            isHiddenPassword = !isHiddenPassword
                        }
                    }
                }
            }
            view?.onTouchEvent(event) ?: true
        }
    }

    private fun tvSignInCreateSpannable() {
        val spannableString =
            addSpannableString(this, getString(R.string.already_have_an_account), 24, 32) {
                startActivity(Intent(this, SignInActivity::class.java))
            }
        binding.tvSignIn.text = spannableString
        binding.tvSignIn.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun validateInputs(): Boolean {
        return if (checkForNull(binding.etEmail, binding.etPassword)) {
            false
        } else if (!isValidEmail(binding.etEmail)) {
            false
        } else isValidPassword(binding.etPassword)
    }

}