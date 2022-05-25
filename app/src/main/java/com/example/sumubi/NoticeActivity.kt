package com.example.sumubi

import android.content.Intent
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

        (application as MasterApplication).service.allAnnounceList().enqueue(
            object : Callback<ArrayList<Announce>> {
                override fun onResponse(
                    call: Call<ArrayList<Announce>>,
                    response: Response<ArrayList<Announce>>
                ) {
                    if (response.isSuccessful) {
                        val announcelist = response.body()
                        val adapter = AnnounceAdapter(
                            announcelist!!,
                            LayoutInflater.from(this@NoticeActivity),
                            this@NoticeActivity
                        )
                        announce_recyclerview.adapter = adapter
                        announce_recyclerview.layoutManager = LinearLayoutManager(this@NoticeActivity)
                    } else {
                        Toast.makeText(this@NoticeActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Announce>>, t: Throwable) {
                    Toast.makeText(this@NoticeActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
    }
}