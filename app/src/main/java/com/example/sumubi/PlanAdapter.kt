package com.example.sumubi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PlanAdapter(
    var planList: ArrayList<Plan>,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val content: TextView

        init {
            title = itemView.findViewById(R.id.title)
            content = itemView.findViewById(R.id.content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.plan_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return planList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(planList.get(position).title)
        holder.content.setText(planList.get(position).content)
    }
}