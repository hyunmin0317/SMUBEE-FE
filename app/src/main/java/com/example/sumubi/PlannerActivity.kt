package com.example.sumubi


import android.view.View
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                            val postList = response.body()
                            Toast.makeText(this@PlannerActivity, postList.toString(), Toast.LENGTH_SHORT).show()
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