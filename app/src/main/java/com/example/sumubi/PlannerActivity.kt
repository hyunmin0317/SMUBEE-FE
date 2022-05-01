package com.example.sumubi


import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_planner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlannerActivity : AppCompatActivity() {
    var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            diaryTextView.visibility = View.VISIBLE

            date = String.format("%04d-%02d-%02d", year, month+1, dayOfMonth)
            diaryTextView.text = date

            (application as MasterApplication).service.getPlanList(date).enqueue(
                object : Callback<ArrayList<Plan>> {
                    override fun onResponse(
                        call: Call<ArrayList<Plan>>,
                        response: Response<ArrayList<Plan>>
                    ) {
                        Toast.makeText(this@PlannerActivity, date, Toast.LENGTH_SHORT).show()

                        if (response.isSuccessful) {
                            val planlist = response.body()
                            val adapter = PlanAdapter(
                                planlist!!,
                                LayoutInflater.from(this@PlannerActivity),
                            )
                            planlist.reverse()
                            plan_recyclerview.adapter = adapter
                            plan_recyclerview.layoutManager = LinearLayoutManager(this@PlannerActivity)
                            Toast.makeText(this@PlannerActivity, planlist.toString(), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@PlannerActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                        Toast.makeText(this@PlannerActivity, "서버 오류", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }
}


class PlanAdapter(
    var planList: ArrayList<Plan>,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView

        init {
            title = itemView.findViewById(R.id.title)
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
    }
}