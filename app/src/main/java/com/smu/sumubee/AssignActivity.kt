package com.smu.sumubee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_assign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign)

        val checked = intent.getBooleanExtra("checked", false)
        var value = 0

        check.text = "완료하지 않은 과제"
        if (checked) {
            check.text = "완료한 과제"
            value = 1
        }

        (application as MasterApplication).service.getAssignList(value).enqueue(
            object : Callback<ArrayList<Data>> {
                override fun onResponse(
                    call: Call<ArrayList<Data>>,
                    response: Response<ArrayList<Data>>
                ) {
                    if (response.isSuccessful) {
                        val datalist = response.body()
                        val adapter = DataAdapter(
                            datalist!!,
                            LayoutInflater.from(this@AssignActivity)
                        )
                        assign_recyclerview.adapter = adapter
                        assign_recyclerview.layoutManager = LinearLayoutManager(this@AssignActivity)
                    } else {
                        Toast.makeText(this@AssignActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Data>>, t: Throwable) {
                }
            }
        )

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
    }
}