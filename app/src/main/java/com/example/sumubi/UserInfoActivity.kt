package com.example.sumubi

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.activity_user_info.board
import kotlinx.android.synthetic.main.activity_user_info.home
import kotlinx.android.synthetic.main.activity_user_info.notice
import kotlinx.android.synthetic.main.activity_user_info.planner
import kotlinx.android.synthetic.main.activity_user_info.user_info
import java.lang.Exception


class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        var appPackage = ""
        var username = getInformation("username")
        var name = getInformation("name")
        var major = getInformation("major")

        user_id.text = username
        user_name.text = name
        user_major.text = major

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }

        logout.setOnClickListener {
            deleteInformation()
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(Intent(this, IntroActivity::class.java))
        }

        check.setOnClickListener {
            appPackage = "kr.co.echeck.smu"
            checkInstalledApp(appPackage)
        }
        potal.setOnClickListener {
            appPackage = "com.tomatosystem.pushapp"
            checkInstalledApp(appPackage)
        }
        library.setOnClickListener {
            appPackage = "kr.ac.smcl.library"
            checkInstalledApp(appPackage)
        }
    }


    fun getInformation(info: String): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val information = sp.getString(info, "null")
        if (information == "null") return null
        else return information
    }

    fun deleteInformation() {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", "null")
        editor.putString("name", "null")
        editor.putString("major", "null")
        editor.putString("token", "null")
        editor.commit()
    }

    fun checkInstalledApp(packageName: String) {
        if (packageManager.getLaunchIntentForPackage(packageName)==null) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packageName)))
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packageName)))
            }
        }
        else
            startActivity(packageManager.getLaunchIntentForPackage(packageName))
    }
}