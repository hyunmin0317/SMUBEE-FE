package com.smu.sumubee

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @POST("users/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("planners/date/{date}/")
    fun getPlanList(
        @Path("date") date: String
    ): Call<ArrayList<Plan>>

    @GET("planners/plan/all/")
    fun allPlanList(): Call<ArrayList<Plan>>

    @GET("planners/class/{status}/")
    fun allClassList(
        @Path("status") status: String
    ): Call<ArrayList<Plan>>

    @DELETE("planners/{pk}/delete/")
    fun deletePlan(
        @Path("pk") pk: Int
    ): Call<Plan>

    @POST("planners/date/{date}/create/")
    fun createPlan(
        @Path("date") date: String,
        @Body plan: Plan
    ): Call<Plan>

    @PUT("planners/{pk}/update/")
    fun updatePlan(
        @Path("pk") pk: Int,
        @Body plan: Plan
    ): Call<Plan>

    @GET("planners/update/")
    fun updatePlan(): Call<ArrayList<Plan>>

    @GET("announcements/{campus}/")
    fun allAnnounceList(
        @Path("campus") campus: String
    ): Call<ArrayList<Announce>>

    @GET("announcements/update/")
    fun updateAnnounce(): Call<ArrayList<Announce>>

    @GET("courses/all/")
    fun getSubjectList(): Call<ArrayList<Subject>>

    @GET("courses/update/")
    fun updateSubject(): Call<ArrayList<Subject>>

    @GET("courses/check/")
    fun getCheckData(): Call<Check>

    @GET("courses/{code}/{category}/")
    fun getDataList(
        @Path("code") code: String,
        @Path("category") category: String
    ): Call<ArrayList<Data>>

    @GET("courses/check/{check}")
    fun getAssignList(
        @Path("check") check: Int
    ): Call<ArrayList<Data>>
}