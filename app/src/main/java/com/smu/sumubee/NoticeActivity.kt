package com.smu.sumubee

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        all.setBackgroundResource(R.drawable.blue_box)
        all.setTextColor(Color.parseColor("#ffffff"))
        changeFilter("all")

        all.setOnClickListener {
            changeDeco()
            all.setBackgroundResource(R.drawable.blue_box)
            all.setTextColor(Color.parseColor("#ffffff"))
            changeFilter("all") }
        complete.setOnClickListener {
            changeDeco()
            complete.setBackgroundResource(R.drawable.blue_box)
            complete.setTextColor(Color.parseColor("#ffffff"))
            changeFilter("complete")
        }
        incomplete.setOnClickListener {
            changeDeco()
            incomplete.setBackgroundResource(R.drawable.blue_box)
            incomplete.setTextColor(Color.parseColor("#ffffff"))
            changeFilter("incomplete")
        }

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
    }

    fun changeFilter(filter: String) {
        (application as MasterApplication).service.allClassList(filter).enqueue(
            object : Callback<ArrayList<Plan>> {
                override fun onResponse(
                    call: Call<ArrayList<Plan>>,
                    response: Response<ArrayList<Plan>>
                ) {
                    if (response.isSuccessful) {
                        val noticelist = response.body()
                        val adapter = NoticeAdapter(
                            noticelist!!,
                            LayoutInflater.from(this@NoticeActivity)
                        )
                        notice_recyclerview.adapter = adapter
                        notice_recyclerview.layoutManager = LinearLayoutManager(this@NoticeActivity)
                    } else {
                        Toast.makeText(this@NoticeActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                    Toast.makeText(this@NoticeActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )

        (application as MasterApplication).service.updateAnnounce().enqueue(
            object : Callback<ArrayList<Announce>> {
                override fun onResponse(call: Call<ArrayList<Announce>>, response: Response<ArrayList<Announce>>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(this@NoticeActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Announce>>, t: Throwable) {
                    Toast.makeText(this@NoticeActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    fun changeDeco() {
        all.setBackgroundColor(Color.parseColor("#E5E5E5"))
        complete.setBackgroundColor(Color.parseColor("#E5E5E5"))
        incomplete.setBackgroundColor(Color.parseColor("#E5E5E5"))
        all.setTextColor(Color.parseColor("#000000"))
        complete.setTextColor(Color.parseColor("#000000"))
        incomplete.setTextColor(Color.parseColor("#000000"))
    }
}