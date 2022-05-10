package com.example.uidesignandroid.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.uidesignandroid.R
import com.example.uidesignandroid.databinding.ActivityOtpVerificationBinding
import com.example.uidesignandroid.utils.Constants.FIFTEEN
import com.example.uidesignandroid.utils.Constants.SEVENTEEN
import com.example.uidesignandroid.utils.Constants.SIXTY_THOUSAND
import com.example.uidesignandroid.utils.Constants.THOUSAND
import com.example.uidesignandroid.utils.addSpannableString
import com.example.uidesignandroid.utils.checkForNull
import com.example.uidesignandroid.utils.hideSoftKeyboard

class OtpVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpVerificationBinding
    private var timer = object : CountDownTimer(SIXTY_THOUSAND, THOUSAND) {
        // Callback function, fired on regular interval
        override fun onTick(millisUntilFinished: Long) {
            val s = (millisUntilFinished / THOUSAND)
            setSpannableText(s.toString())
        }

        override fun onFinish() {
            binding.tvCounter.text = getString(R.string.sent_code_again)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        binding.customToolbar.tvTitle.text = getString(R.string.forgot_password)
        setContentView(binding.root)

        setUpOtpInputs()
        setUpOtpKeyInputs()

        binding.customToolbar.arrowImageView.setOnClickListener {
            finish()
        }

        binding.btnVerify.setOnClickListener {
            if (!checkForNull(
                    binding.etDigit1,
                    binding.etDigit2,
                    binding.etDigit3,
                    binding.etDigit4
                )
            )
                startActivity(Intent(this, CreateNewPasswordActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun countDownTimer() {
        timer.start()
    }

    private fun setSpannableText(string: String) {
        val stringWithCounterValue = getString(R.string.resend_code_in) + " " + string + " s"
        binding.tvCounter.text =
            addSpannableString(this, stringWithCounterValue, FIFTEEN, SEVENTEEN) {}
    }

    private fun setUpOtpInputs() {
        binding.etDigit1.addTextChangedListener {
            if (it.toString().trim().isNotEmpty()) {
                binding.etDigit2.requestFocus()
            }
        }
        binding.etDigit2.addTextChangedListener {
            if (it.toString().trim().isNotEmpty()) {
                binding.etDigit3.requestFocus()
            }
        }
        binding.etDigit3.addTextChangedListener {
            if (it.toString().trim().isNotEmpty()) {
                binding.etDigit4.requestFocus()
            }
        }
        binding.etDigit4.addTextChangedListener {
            if (it.toString().trim().isNotEmpty()) {
                hideSoftKeyboard()
                binding.etDigit4.clearFocus()
                countDownTimer()
            }
        }
    }

    private fun setUpOtpKeyInputs() {
        binding.etDigit1.setOnKeyListener { view, keyCode, keyEvent ->
            keyEvent.let {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (binding.etDigit1.text.isNullOrEmpty()) {
                        return@setOnKeyListener true
                    } else {
                        binding.etDigit1.text?.clear()
                    }
                }
            }
            false
        }

        binding.etDigit2.setOnKeyListener { view, keyCode, keyEvent ->
            keyEvent.let {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (binding.etDigit2.text.isNullOrEmpty()) {
                        binding.etDigit1.requestFocus()
                        return@setOnKeyListener true
                    } else {
                        binding.etDigit2.text?.clear()
                    }
                }
            }
            false
        }

        binding.etDigit3.setOnKeyListener { view, keyCode, keyEvent ->
            keyEvent.let {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (binding.etDigit3.text.isNullOrEmpty()) {
                        binding.etDigit2.requestFocus()
                        return@setOnKeyListener true
                    } else {
                        binding.etDigit3.text?.clear()
                    }
                }
            }
            false
        }

        binding.etDigit4.setOnKeyListener { view, keyCode, keyEvent ->
            keyEvent.let {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (binding.etDigit4.text.isNullOrEmpty()) {
                        binding.etDigit3.requestFocus()
                        return@setOnKeyListener true
                    } else {
                        binding.etDigit4.text?.clear()
                    }
                }
            }
            false
        }
    }
}