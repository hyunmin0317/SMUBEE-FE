package com.smu.sumubee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_create.board
import kotlinx.android.synthetic.main.activity_create.home
import kotlinx.android.synthetic.main.activity_create.notice
import kotlinx.android.synthetic.main.activity_create.planner
import kotlinx.android.synthetic.main.activity_create.user_info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val intent = intent
        val date = intent.getStringExtra("date").toString()
        date_view.text = date

        save_plan.setOnClickListener {
            val title = title_view.text.toString()
            val content = content_view.text.toString()
            uploadPost(date, title, content)
        }

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
    }

    fun uploadPost(date: String, title: String, content: String) {

        val plan = Plan(title=title, content=content)
        (application as MasterApplication).service.createPlan(
            date, plan
        ).enqueue(object : Callback<Plan> {
            override fun onResponse(call: Call<Plan>, response: Response<Plan>) {
                if (response.isSuccessful) {
                    finish()
                    startActivity(Intent(this@CreateActivity, PlannerActivity::class.java))
                } else {
                    Toast.makeText(this@CreateActivity, "400 Bad Request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Plan>, t: Throwable) {
                Toast.makeText(this@CreateActivity, "서버 오류", Toast.LENGTH_LONG).show()
            }
        })
    }
}