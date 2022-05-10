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
import com.example.uidesignandroid.databinding.ActivitySignInBinding
import com.example.uidesignandroid.models.SignInResponse
import com.example.uidesignandroid.utils.addSpannableString
import com.example.uidesignandroid.utils.isValidEmail
import com.example.uidesignandroid.utils.passwordVisibility
import com.example.uidesignandroid.viewmodels.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.uidesignandroid.R
import com.example.uidesignandroid.databinding.ActivitySignInBinding
import com.example.uidesignandroid.utils.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var isHiddenPassword = true
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            if (validateInputs()) {
                binding.progressBar.visibility = View.VISIBLE
                lifecycleScope.launch(Dispatchers.IO) {
                    signInViewModel.logIn(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString(),
                        SignInResponse::class.java
                    )
                }
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
        setUpViewModelObservers()

    }

    private fun setUpViewModelObservers() {
        signInViewModel.apply {
            isValidUser.observe(this@SignInActivity) {
                if (it) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@SignInActivity, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    finish()
                }
            }
            errorMessage.observe(this@SignInActivity) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@SignInActivity, it, Toast.LENGTH_SHORT).show()
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