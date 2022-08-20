package com.example.sumubi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.board
import kotlinx.android.synthetic.main.activity_main.check
import kotlinx.android.synthetic.main.activity_main.home
import kotlinx.android.synthetic.main.activity_main.planner
import kotlinx.android.synthetic.main.activity_main.user_info
import kotlinx.android.synthetic.main.activity_user_info.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appPackage = ""
        var username = getInformation("name")
        name.text = username


        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }

        check.setOnClickListener {
            appPackage = "kr.co.echeck.smu"
            checkInstalledApp(appPackage)
        }
    }

    fun getInformation(info: String): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val information = sp.getString(info, "null")
        if (information == "null") return null
        else return information
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