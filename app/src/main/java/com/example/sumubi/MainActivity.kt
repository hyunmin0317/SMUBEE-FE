package com.example.sumubi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.board
import kotlinx.android.synthetic.main.activity_main.home
import kotlinx.android.synthetic.main.activity_main.notice
import kotlinx.android.synthetic.main.activity_main.planner
import kotlinx.android.synthetic.main.activity_main.user_info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appPackage = ""
        var username = getInformation("name")
        name.text = username


        (application as MasterApplication).service.getSubjectList().enqueue(
            object : Callback<ArrayList<Subject>> {
                override fun onResponse(
                    call: Call<ArrayList<Subject>>,
                    response: Response<ArrayList<Subject>>
                ) {

                    if (response.isSuccessful) {
                        val subjectlist = response.body()
                        val adapter = SubjectAdapter(
                            subjectlist!!,
                            LayoutInflater.from(this@MainActivity),
                            this@MainActivity
                        )
                        course_recyclerview.adapter = adapter
                        course_recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    } else {
                        Toast.makeText(this@MainActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Subject>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )



        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }

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