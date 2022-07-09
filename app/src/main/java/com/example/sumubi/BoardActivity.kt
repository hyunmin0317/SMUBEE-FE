package com.example.sumubi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_board.board
import kotlinx.android.synthetic.main.activity_board.home
import kotlinx.android.synthetic.main.activity_board.notice
import kotlinx.android.synthetic.main.activity_board.planner
import kotlinx.android.synthetic.main.activity_board.user_info
import kotlinx.android.synthetic.main.activity_planner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        all.setBackgroundResource(R.drawable.blue_box)
        all.setTextColor(Color.parseColor("#ffffff"))
        changeFilter("all")

        all.setOnClickListener {
            changeDeco()
            all.setBackgroundResource(R.drawable.blue_box)
            all.setTextColor(Color.parseColor("#ffffff"))
            changeFilter("all") }
        seoul.setOnClickListener {
            changeDeco()
            seoul.setBackgroundResource(R.drawable.blue_box)
            seoul.setTextColor(Color.parseColor("#ffffff"))
            changeFilter("seoul")
        }
        cheonan.setOnClickListener {
            changeDeco()
            cheonan.setBackgroundResource(R.drawable.blue_box)
            cheonan.setTextColor(Color.parseColor("#ffffff"))
            changeFilter("cheonan")
        }

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }

    }

    fun changeFilter(filter: String) {
        (application as MasterApplication).service.allAnnounceList(filter).enqueue(
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
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(this@BoardActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@BoardActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    fun changeDeco() {
        all.setBackgroundColor(Color.parseColor("#E5E5E5"))
        seoul.setBackgroundColor(Color.parseColor("#E5E5E5"))
        cheonan.setBackgroundColor(Color.parseColor("#E5E5E5"))
        all.setTextColor(Color.parseColor("#000000"))
        seoul.setTextColor(Color.parseColor("#000000"))
        cheonan.setTextColor(Color.parseColor("#000000"))
    }
}