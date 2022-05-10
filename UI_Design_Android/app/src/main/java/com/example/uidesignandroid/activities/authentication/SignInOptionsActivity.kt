package com.example.uidesignandroid.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.uidesignandroid.R
import com.example.uidesignandroid.databinding.ActivitySignInOptionsBinding
import com.example.uidesignandroid.utils.addSpannableString

class SignInOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvSignInCreateSpannable()

        binding.btnSignInWithPassword.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun tvSignInCreateSpannable() {
        val spannableString =
            addSpannableString(this, getString(R.string.don_t_have_an_account), 23, 30) {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        binding.tvDoNotHaveAcc.text = spannableString
        binding.tvDoNotHaveAcc.movementMethod = LinkMovementMethod.getInstance()
    }
}