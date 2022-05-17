package com.example.uidesignandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesignandroid.R
import com.example.uidesignandroid.models.OnBoardingItem


class OnBoardingAdapter(private var onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_onboarding_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.setOnBoardingData(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvSubtitle: TextView = view.findViewById(R.id.tvSubtitle)
        private val imgOnBoarding: ImageView = view.findViewById(R.id.imgOnBoarding)

        fun setOnBoardingData(onBoardingItem: OnBoardingItem) {
            tvTitle.text = onBoardingItem.title
            tvSubtitle.text = onBoardingItem.description
            imgOnBoarding.setImageResource(onBoardingItem.image)
        }
    }

}