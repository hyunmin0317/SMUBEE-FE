package com.example.sumubi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create.*
import okhttp3.MediaType
import okhttp3.RequestBody
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