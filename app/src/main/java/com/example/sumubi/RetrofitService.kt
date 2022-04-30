package com.example.sumubi

import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("post/all/")
    fun getAllPosts(): Call<ArrayList<Post>>

    @Multipart
    @POST("post/create/")
    fun uploadPost(
        @Part image : MultipartBody.Part,
        @Part ("content")requestBody : RequestBody
    ):Call<Post>

    @GET("post/mylist/")
    fun getUserPostList():Call<ArrayList<Post>>

    @DELETE("post/{pk}/delete/")
    fun deletePost(
        @Path("pk") pk: Int
    ):Call<Post>

    @Multipart
    @PUT("post/{pk}/update/")
    fun updatePost(
        @Path("pk") pk: Int,
        @Part image: MultipartBody.Part,
        @Part ("content") requestBody: RequestBody
    ):Call<Post>
}