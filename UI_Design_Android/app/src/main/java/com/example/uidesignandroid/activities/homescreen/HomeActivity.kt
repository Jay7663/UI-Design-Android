package com.example.uidesignandroid.activities.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uidesignandroid.R
import com.example.uidesignandroid.databinding.ActivityCreateNewPasswordBinding
import com.example.uidesignandroid.databinding.ActivityHomeBinding
import com.example.uidesignandroid.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()

        binding.fragmentContainer

        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> {
                    Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show()
                }
                R.id.itemSaved -> {
                    Toast.makeText(this, "Saved Selected", Toast.LENGTH_SHORT).show()
                }
                R.id.itemBooking -> {
                    Toast.makeText(this, "Booking Selected", Toast.LENGTH_SHORT).show()
                }
                R.id.itemProfile -> {
                    Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

    }
}