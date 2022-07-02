package com.example.sumubi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

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
                            LayoutInflater.from(this@BoardActivity),
                            this@BoardActivity
                        )
                        announce_recyclerview.adapter = adapter
                        announce_recyclerview.layoutManager = LinearLayoutManager(this@BoardActivity)
                    } else {
                        Toast.makeText(this@BoardActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Announce>>, t: Throwable) {
                    Toast.makeText(this@BoardActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )

        (application as MasterApplication).service.updateAnnounce().enqueue(
            object : Callback<Announce> {
                override fun onResponse(call: Call<Announce>, response: Response<Announce>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(this@BoardActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Announce>, t: Throwable) {
                    Toast.makeText(this@BoardActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }



    }
}