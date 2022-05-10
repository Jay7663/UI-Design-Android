package com.example.uidesignandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesignandroid.R

class LocationsAdapter(private var arrayList: ArrayList<String>) :
    RecyclerView.Adapter<LocationsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_recycler_item_location, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            tvLocationName.text = arrayList[position]
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvLocationName: TextView = view.findViewById(R.id.tvLocationName)
    }
}