package com.example.sumubi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_planner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        (application as MasterApplication).service.allPlanList().enqueue(
            object : Callback<ArrayList<Plan>> {
                override fun onResponse(
                    call: Call<ArrayList<Plan>>,
                    response: Response<ArrayList<Plan>>
                ) {

                    if (response.isSuccessful) {
                        val planlist = response.body()
                        val adapter = PlanAdapter(
                            planlist!!,
                            LayoutInflater.from(this@MainActivity),
                        )
                        planlist.reverse()
                        plan_recyclerview.adapter = adapter
                        plan_recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    } else {
                        Toast.makeText(this@MainActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}