package com.example.sumubi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_create.board
import kotlinx.android.synthetic.main.activity_create.home
import kotlinx.android.synthetic.main.activity_create.notice
import kotlinx.android.synthetic.main.activity_create.planner
import kotlinx.android.synthetic.main.activity_create.user_info
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        val pk = intent.getIntExtra("pk", -1)

        save_plan.setOnClickListener {
            val title = title_view.text.toString()
            val content = content_view.text.toString()
            uploadPost(pk, title, content)
        }

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
    }

    fun uploadPost(pk: Int, title: String, content: String) {

        val plan = Plan(title=title, content=content)
        (application as MasterApplication).service.updatePlan(
            pk, plan
        ).enqueue(object : Callback<Plan> {
            override fun onResponse(call: Call<Plan>, response: Response<Plan>) {
                if (response.isSuccessful) {
                    finish()
                    startActivity(Intent(this@UpdateActivity, PlannerActivity::class.java))
                } else {
                    Toast.makeText(this@UpdateActivity, "400 Bad Request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Plan>, t: Throwable) {
                Toast.makeText(this@UpdateActivity, "서버 오류", Toast.LENGTH_LONG).show()
            }
        })
    }
}