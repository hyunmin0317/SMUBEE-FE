package com.example.sumubi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DataAdapter(
    var dataList: ArrayList<Data>,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView
        val title: TextView
        val date: TextView
        val status: TextView


        init {
            type = itemView.findViewById(R.id.type)
            title = itemView.findViewById(R.id.title)
            date = itemView.findViewById(R.id.date)
            status = itemView.findViewById(R.id.status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.data_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(dataList.get(position).title)
        holder.date.setText(dataList.get(position).date)
        holder.status.setText(dataList.get(position).status)

        var category = dataList.get(position).category

        if (category == "course") {
            holder.type.setText("강의")
        } else if (category == "assign") {
            holder.type.setText("과제")
        }
    }
}