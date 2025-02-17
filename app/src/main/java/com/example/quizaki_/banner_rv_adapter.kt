package com.example.quizaki_

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class banner_rv_adapter(val bannerdata :ArrayList<banner_rv_dc>):RecyclerView.Adapter<banner_rv_adapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.imghomerv1)
        val eventnamerv1_: TextView = view.findViewById(R.id.eventnamerv1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bannerdata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = bannerdata[position]
        holder.eventnamerv1_.text = item.textView.toString()
        Glide.with(holder.image.context) // Use Glide for image loading
            .load(item.imageUrl)
            .placeholder(R.drawable.home) // Show a placeholder image
            .into(holder.image)
    }
}