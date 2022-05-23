package com.example.uidesignandroid.activities.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.example.uidesignandroid.R
import com.example.uidesignandroid.activities.homescreen.HomeActivity
import com.example.uidesignandroid.base.BaseActivity
import com.example.uidesignandroid.databinding.ActivitySignInBinding
import com.example.uidesignandroid.utils.Constants.SEVENTY
import com.example.uidesignandroid.utils.Constants.THIRTY
import com.example.uidesignandroid.utils.Constants.TWENTY_THREE
import com.example.uidesignandroid.utils.Constants.TWO
import com.example.uidesignandroid.utils.Constants.ZERO
import com.example.uidesignandroid.utils.addSpannableString
import com.example.uidesignandroid.utils.passwordVisibility
import com.example.uidesignandroid.viewmodels.SignInViewModel

class SignInActivity : BaseActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var isHiddenPassword = true
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetUp()
        putSpannableStringInTV()
        setUpToggle()
        setUpViewModelObservers()
    }

    private fun initialSetUp() {
        binding.btnSignIn.setOnClickListener {
            if (validateInputs()) {
                binding.progressBar.visibility = View.VISIBLE
                signInViewModel.signIn(
                    this,
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
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
    }

    private fun setUpViewModelObservers() {
        signInViewModel.isValidUser.observe(this) {
            binding.progressBar.visibility = View.GONE
            if (it) {
                Toast.makeText(this, getString(R.string.user_added), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpToggle() {
        binding.etPassword.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        if (event.rawX >= binding.etPassword.right - binding.etPassword.compoundDrawables[TWO].bounds.width() - SEVENTY) {
                            val selection = binding.etPassword.selectionEnd
                            binding.etPassword.passwordVisibility(isHiddenPassword)
                            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.custom_lock,
                                ZERO,
                                if (isHiddenPassword) R.drawable.custom_eye_hidden else R.drawable.custom_eye_show,
                                ZERO
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
            addSpannableString(
                this,
                getString(R.string.don_t_have_an_account),
                TWENTY_THREE,
                THIRTY
            ) {
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