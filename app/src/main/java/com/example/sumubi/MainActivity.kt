package com.example.sumubi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
    }
}