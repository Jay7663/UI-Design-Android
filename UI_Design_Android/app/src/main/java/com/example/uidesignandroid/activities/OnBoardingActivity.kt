package com.example.uidesignandroid.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.uidesignandroid.R
import com.example.uidesignandroid.activities.authentication.SignInOptionsActivity
import com.example.uidesignandroid.adapters.OnBoardingAdapter
import com.example.uidesignandroid.databinding.ActivityOnboardingBinding
import com.example.uidesignandroid.models.OnBoardingItem


class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnBoardingItem()

        val onBoardingViewPager = findViewById<ViewPager2>(R.id.onBoardingViewPager)
        onBoardingViewPager.adapter = onBoardingAdapter

        setOnBoardingIndicator()
        setCurrentOnBoardingIndicators(0)

        onBoardingViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnBoardingIndicators(position)
            }
        })

        binding.btnOnBoardingAction.setOnClickListener {
            if (onBoardingViewPager.getCurrentItem() + 1 < onBoardingAdapter.itemCount) {
                onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1)
            } else {
                startActivity(Intent(this, SignInOptionsActivity::class.java))
                finish()
            }
        }

        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, SignInOptionsActivity::class.java))
            finish()
        }
    }

    private fun setOnBoardingIndicator() {
        val indicators: Array<ImageView?> = arrayOfNulls(onBoardingAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.ic_page_control_not_selected
                )
            )
            indicators[i]?.layoutParams = layoutParams
            binding.layoutOnBoardingIndicators.addView(indicators[i])
        }
    }

    private fun setCurrentOnBoardingIndicators(index: Int) {
        val childCount = binding.layoutOnBoardingIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutOnBoardingIndicators.getChildAt(i) as ImageView
            if (i == index) {
                // Active
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_page_control_selected
                    )
                )
            } else {
                // Not-Active
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_page_control_not_selected
                    )
                )
            }
        }
        if (index == onBoardingAdapter.itemCount - 1) {
            binding.btnOnBoardingAction.text = getString(R.string.get_started)
        } else {
            binding.btnOnBoardingAction.text = getString(R.string.next)
        }
    }

    private fun setOnBoardingItem() {
        val onBoardingItems: MutableList<OnBoardingItem> = ArrayList()
        onBoardingItems.apply {
            add(
                OnBoardingItem(
                    R.drawable.frame1,
                    getString(R.string.frame1_title),
                    getString(R.string.frame1_description)
                )
            )
            add(
                OnBoardingItem(
                    R.drawable.frame2,
                    getString(R.string.frame2_title),
                    getString(R.string.frame2_description)
                )
            )
            add(
                OnBoardingItem(
                    R.drawable.frame3,
                    getString(R.string.frame3_title),
                    getString(R.string.frame3_description)
                )
            )
        }
        onBoardingAdapter = OnBoardingAdapter(onBoardingItems)
    }
}