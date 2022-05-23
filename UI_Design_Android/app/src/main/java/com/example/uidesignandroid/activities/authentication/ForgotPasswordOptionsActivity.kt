package com.example.uidesignandroid.activities.authentication

import android.content.Intent
import android.os.Bundle
import com.example.uidesignandroid.R
import com.example.uidesignandroid.base.BaseActivity
import com.example.uidesignandroid.databinding.ActivityForgotPasswordOptionsBinding

class ForgotPasswordOptionsActivity : BaseActivity() {

    private lateinit var binding: ActivityForgotPasswordOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordOptionsBinding.inflate(layoutInflater)
        binding.customToolbar.tvTitle.text = getString(R.string.forgot_password)
        setContentView(binding.root)
        binding.smsSelection.isSelected = true

        binding.smsSelection.setOnClickListener {
            it.isSelected = true
            binding.emailSelection.isSelected = false
        }

        binding.emailSelection.setOnClickListener {
            it.isSelected = true
            binding.smsSelection.isSelected = false
        }

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, OtpVerificationActivity::class.java))
        }

        binding.customToolbar.arrowImageView.setOnClickListener {
            finish()
        }
    }
}