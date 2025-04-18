package com.example.quizaki_

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quizaki_.banner_rv1_adapter.ViewHolder

class banner_rv2_adapter(var bannerRv2Dc: List<banner_rv2_dc>): RecyclerView.Adapter<banner_rv2_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): banner_rv2_adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homerecyclerview2, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bannerRv2Dc.size
    }

    override fun onBindViewHolder(holder: banner_rv2_adapter.ViewHolder, position: Int) {
        val item = bannerRv2Dc[position]
        holder.eventnamerv2.text = item.name
        Glide.with(holder.image2.context) // Use Glide for image loading
            .load(item.imgurl)
            .placeholder(R.drawable.home) // Show a placeholder image
            .into(holder.image2)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image2: ImageView = view.findViewById(R.id.imghomerv2)
        val eventnamerv2: TextView = view.findViewById(R.id.eventnamerv2)
    }
}