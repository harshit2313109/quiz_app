package com.example.quizaki_

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizaki_.banner_rv2_adapter.ViewHolder

class banner_rv3_adapter(var bannerRv3Dc: List<banner_rv3_dc>): RecyclerView.Adapter<banner_rv3_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): banner_rv3_adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homerecyclerview3, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: banner_rv3_adapter.ViewHolder, position: Int) {
        val item = bannerRv3Dc[position]
        holder.competitionname.text = item.competitionname
    }

    override fun getItemCount(): Int {
        return bannerRv3Dc.size

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val competitionname :TextView = view.findViewById(R.id.eventnamerv3)
}
}