package com.example.sumubi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_subject.*
import kotlinx.android.synthetic.main.activity_subject.board
import kotlinx.android.synthetic.main.activity_subject.home
import kotlinx.android.synthetic.main.activity_subject.notice
import kotlinx.android.synthetic.main.activity_subject.planner
import kotlinx.android.synthetic.main.activity_subject.user_info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        val name = intent.getStringExtra("name").toString()
        val prof = intent.getStringExtra("prof").toString()
        val code = intent.getStringExtra("code").toString()

        subject_name.text = name
        professor.text = prof + "교수님"

        course.setBackgroundResource(R.drawable.whitebox)
        course.setTextColor(Color.parseColor("#000000"))
        changeFilter(code, "course")

        course.setOnClickListener {
            changeDeco()
            course.setBackgroundResource(R.drawable.whitebox)
            course.setTextColor(Color.parseColor("#000000"))
            changeFilter(code, "course")
        }

        assign.setOnClickListener {
            changeDeco()
            assign.setBackgroundResource(R.drawable.whitebox)
            assign.setTextColor(Color.parseColor("#000000"))
            changeFilter(code, "assign")
        }

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
    }

    fun changeFilter(code: String, category: String) {
        (application as MasterApplication).service.getDataList(code, category).enqueue(
            object : Callback<ArrayList<Data>> {
                override fun onResponse(
                    call: Call<ArrayList<Data>>,
                    response: Response<ArrayList<Data>>
                ) {
                    if (response.isSuccessful) {
                        val datalist = response.body()
                        val adapter = DataAdapter(
                            datalist!!,
                            LayoutInflater.from(this@SubjectActivity)
                        )
                        subject_recyclerview.adapter = adapter
                        subject_recyclerview.layoutManager = LinearLayoutManager(this@SubjectActivity)
                    } else {
                        Toast.makeText(this@SubjectActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Data>>, t: Throwable) {
                    Toast.makeText(this@SubjectActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    fun changeDeco() {
        course.setBackgroundColor(Color.parseColor("#142D56"))
        assign.setBackgroundColor(Color.parseColor("#142D56"))
        course.setTextColor(Color.parseColor("#ffffff"))
        assign.setTextColor(Color.parseColor("#ffffff"))
    }
}