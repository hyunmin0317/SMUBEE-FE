package com.sangmyung.sumubi

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlanAdapter(
    var planList: ArrayList<Plan>,
    val inflater: LayoutInflater,
    val activity: Activity
) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val content: TextView

        init {
            title = itemView.findViewById(R.id.title)
            content = itemView.findViewById(R.id.content)

            itemView.findViewById<TextView>(R.id.delete).setOnClickListener {
                (activity.application as MasterApplication).service.deletePlan(
                    planList.get(adapterPosition).id!!
                ).enqueue(object : Callback<Plan> {

                    override fun onResponse(call: Call<Plan>, response: Response<Plan>) {
                        if (response.isSuccessful) {
                            Toast.makeText(activity, "삭제되었습니다.", Toast.LENGTH_LONG).show()
                            activity.startActivity(Intent(activity, PlannerActivity::class.java))
                        } else {
                            Toast.makeText(activity, "삭제 오류", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Plan>, t: Throwable) {
                        Toast.makeText(activity, "서버 오류", Toast.LENGTH_LONG).show()
                    }
                })
            }

            itemView.findViewById<TextView>(R.id.update).setOnClickListener {
                val intent = Intent(activity, UpdateActivity::class.java)
                val plan = planList.get(adapterPosition)
                intent.putExtra("pk", plan.id)
                intent.putExtra("date", plan.date)
                activity.startActivity(intent)
            }
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