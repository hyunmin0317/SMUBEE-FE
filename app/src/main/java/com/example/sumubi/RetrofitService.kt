package com.example.sumubi

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("planner/date/{date}/")
    fun getPlanList(
        @Path("date") date: String
    ): Call<ArrayList<Plan>>

    @GET("planner/plan/all/")
    fun allPlanList(): Call<ArrayList<Plan>>

    @GET("planner/class/all/")
    fun allClassList(): Call<ArrayList<Plan>>

    @DELETE("planner/{pk}/delete/")
    fun deletePlan(
        @Path("pk") pk: Int
    ): Call<Plan>

    @POST("planner/date/{date}/create/")
    fun createPlan(
        @Path("date") date: String,
        @Body plan: Plan
    ): Call<Plan>

    @PUT("planner/{pk}/update/")
    fun updatePlan(
        @Path("pk") pk: Int,
        @Body plan: Plan
    ): Call<Plan>
}