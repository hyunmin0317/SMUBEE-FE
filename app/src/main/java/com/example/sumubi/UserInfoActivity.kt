package com.example.sumubi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_info.*


class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        var username = getUserName()
        if (username == null)
            username = ""
        user.text = username

        home.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        mypage.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        upload.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }

        logout.setOnClickListener {
            deleteUserToken()
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(Intent(this, IntroActivity::class.java))
        }
    }


    fun getUserName(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val username = sp.getString("username", "null")
        if (username == "null") return null
        else return username
    }

    fun deleteUserToken() {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", "null")
        editor.putString("token", "null")
        editor.commit()
    }
}