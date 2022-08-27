package com.example.sumubi


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_planner.*
import kotlinx.android.synthetic.main.activity_planner.board
import kotlinx.android.synthetic.main.activity_planner.home
import kotlinx.android.synthetic.main.activity_planner.notice
import kotlinx.android.synthetic.main.activity_planner.planner
import kotlinx.android.synthetic.main.activity_planner.user_info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class PlannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var Date = simpleDateFormat.format(System.currentTimeMillis())
        changeDate(Date)
        getPlan()
        getClass()
        updateClass()

        create.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra("date", Date)
            startActivity(intent)
        }

        calendarView.setSelectedDate(CalendarDay.today())
        calendarView.addDecorators(SaturdayDecorator(), SundayDecorator(), SelectorDecorator(this), OneDayDecorator())

        calendarView.setOnDateChangedListener { widget, date, selected ->
            Date = String.format("%04d-%02d-%02d", date.year, date.month+1, date.day)
            changeDate(Date)
        }

        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        planner.setOnClickListener { startActivity(Intent(this, PlannerActivity::class.java)) }
        board.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        user_info.setOnClickListener { startActivity(Intent(this, UserInfoActivity::class.java)) }
        notice.setOnClickListener { startActivity(Intent(this, NoticeActivity::class.java)) }
    }

    fun changeDate(date: String) {
        date_view.text = date

        (application as MasterApplication).service.getPlanList(date).enqueue(
            object : Callback<ArrayList<Plan>> {
                override fun onResponse(
                    call: Call<ArrayList<Plan>>,
                    response: Response<ArrayList<Plan>>
                ) {

                    if (response.isSuccessful) {
                        val planlist = response.body()
                        val adapter = PlanAdapter(
                            planlist!!,
                            LayoutInflater.from(this@PlannerActivity),
                            this@PlannerActivity
                        )
                        plan_recyclerview.adapter = adapter
                        plan_recyclerview.layoutManager = LinearLayoutManager(this@PlannerActivity)
                    } else {
                        Toast.makeText(this@PlannerActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                    Toast.makeText(this@PlannerActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    fun getPlan() {
        (application as MasterApplication).service.allPlanList().enqueue(
            object : Callback<ArrayList<Plan>> {
                override fun onResponse(
                    call: Call<ArrayList<Plan>>,
                    response: Response<ArrayList<Plan>>
                ) {

                    if (response.isSuccessful) {
                        val calendar = ArrayList<CalendarDay>()
                        val planlist = response.body()

                        for (plan in planlist!!) {
                            val date = plan.date!!.split('T')
                            val Date = date[0].split('-')
                            calendar.add(CalendarDay.from(Date[0].toInt(), Date[1].toInt()-1, Date[2].toInt()))
                        }

                        calendarView.addDecorator(EventDecorator(Color.BLUE, calendar))
                    } else {
                        Toast.makeText(this@PlannerActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                    Toast.makeText(this@PlannerActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    fun getClass() {
        (application as MasterApplication).service.allClassList().enqueue(
            object : Callback<ArrayList<Plan>> {
                override fun onResponse(
                    call: Call<ArrayList<Plan>>,
                    response: Response<ArrayList<Plan>>
                ) {

                    if (response.isSuccessful) {
                        val calendar = ArrayList<CalendarDay>()
                        val planlist = response.body()

                        for (plan in planlist!!) {
                            val date = plan.date!!.split('T')
                            val Date = date[0].split('-')
                            calendar.add(CalendarDay.from(Date[0].toInt(), Date[1].toInt()-1, Date[2].toInt()))
                        }

                        calendarView.addDecorator(EventDecorator(Color.RED, calendar))
                    } else {
                        Toast.makeText(this@PlannerActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                    Toast.makeText(this@PlannerActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    fun updateClass() {
        (application as MasterApplication).service.updatePlan().enqueue(
            object : Callback<ArrayList<Plan>> {
                override fun onResponse(call: Call<ArrayList<Plan>>, response: Response<ArrayList<Plan>>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(this@PlannerActivity, "400 Bad Request", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Plan>>, t: Throwable) {
                    Toast.makeText(this@PlannerActivity, "서버 오류", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}