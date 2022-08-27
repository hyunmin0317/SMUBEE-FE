package com.example.sumubi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NoticeAdapter(
    var noticeList: ArrayList<Plan>,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val date: TextView
        val status: TextView
        val type: TextView
        val checked: TextView
        val course: TextView


        init {
            title = itemView.findViewById(R.id.title)
            date = itemView.findViewById(R.id.date)
            status = itemView.findViewById(R.id.status)
            type = itemView.findViewById(R.id.type)
            checked = itemView.findViewById(R.id.checked)
            course = itemView.findViewById(R.id.course)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.notice_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = noticeList.get(position).category
        val checked = noticeList.get(position).checked
        if (category == "assign")
            category = "과제"
        else
            category = "강의"

        if (checked!!)
            holder.checked.setBackgroundResource(R.drawable.completeb)

        holder.title.setText(noticeList.get(position).title)
        holder.date.setText(noticeList.get(position).date!!.substring(0,10))
        holder.status.setText(noticeList.get(position).status)
        holder.course.setText(noticeList.get(position).course)
        holder.type.setText(category)
    }
}