package com.lit.books.interfaces

import com.lit.books.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun userLogin(@Body userData: UserInfo): Call<LoginResp>

    @POST("register")
    fun userRegister(@Body userData: UserInfoSignUp): Call<SignUpResp>

    @POST("payment")
    fun userPayment(@Body userData: PaymentInfo): Call<SignUpResp>
//    @Headers("Content-Type: application/json")
//    @POST("change_password")
//    fun change_password(@Body passwordPost: PasswordPost): Call<TaskResp>
//
//

    @POST("check_payment")
    fun paymentCheck(@Body userData: PaymentInfo): Call<SignUpResp>



    @Headers("Content-Type: application/json")
    @POST("books")
    fun books(): Call<BookResp>


    @POST("book_activities")
    fun bookActivities(@Body activityModel: ActivityModel): Call<BookResp>


//    @Headers("Content-Type: application/json")
//    @POST("book_activities")
//    fun bookActivities(@Body activityModel: ActivityModel): Call<BookResp>
}