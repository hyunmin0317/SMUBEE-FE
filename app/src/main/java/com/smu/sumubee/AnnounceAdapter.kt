package com.smu.sumubee

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
        val created_date: TextView
        val views: TextView

        init {
            title = itemView.findViewById(R.id.title)
            campus = itemView.findViewById(R.id.campus)
            created_date = itemView.findViewById(R.id.created_date)
            views = itemView.findViewById(R.id.views)

            itemView.findViewById<TextView>(R.id.title).setOnClickListener {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(announceList.get(adapterPosition).more_link)))
            }
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
        holder.created_date.setText(announceList.get(position).created_date)
        holder.views.setText(announceList.get(position).views.toString())

        var campus = announceList.get(position).campus

        if (campus == "seoul") {
            holder.campus.setText("서울")
            holder.campus.setTextColor(Color.parseColor("#2653B2"))
        } else if (campus == "cheonan") {
            holder.campus.setText("천안")
            holder.campus.setTextColor(Color.parseColor("#96163D"))
        } else {
            holder.campus.setText("상명")
            holder.campus.setTextColor(Color.parseColor("#E75581"))
        }
    }
}