package com.example.sumubi

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AnnounceAdapter(
    var announceList: ArrayList<Announce>,
    val inflater: LayoutInflater,
    val activity: Activity
) : RecyclerView.Adapter<AnnounceAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val campus: TextView
        val number: TextView
        val created_date: TextView
        val views: TextView

        init {
            title = itemView.findViewById(R.id.title)
            campus = itemView.findViewById(R.id.campus)
            number = itemView.findViewById(R.id.number)
            created_date = itemView.findViewById(R.id.created_date)
            views = itemView.findViewById(R.id.views)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.announce_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return announceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(announceList.get(position).title)
        holder.campus.setText(announceList.get(position).campus)
        holder.number.setText(announceList.get(position).number.toString())
        holder.created_date.setText(announceList.get(position).created_date)
        holder.views.setText(announceList.get(position).views.toString())
    }
}