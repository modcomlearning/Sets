package com.lit.books.services

import com.lit.books.interfaces.RestApi
import com.lit.books.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class RestApiService {
    fun forgotChange(userData: ForgotChange, onResult: (LoginResp?) -> Unit){
        val retrofit = ServiceBuilderLogin.buildService(RestApi::class.java)
        retrofit.userChange(userData).enqueue(
            object : Callback<LoginResp> {
                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                    //val code = response.code()
                    println(""+response.body())
                    onResult(response.body())

                }
            }
        )
    }//End
    fun forgotUser(userData: Forgot, onResult: (LoginResp?) -> Unit){
        val retrofit = ServiceBuilderLogin.buildService(RestApi::class.java)
        retrofit.userForgot(userData).enqueue(
            object : Callback<LoginResp> {
                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                    //val code = response.code()
                    println(""+response.body())
                    onResult(response.body())

                }
            }
        )
    }//End
    fun loginUser(userData: UserInfo, onResult: (LoginResp?) -> Unit){
        val retrofit = ServiceBuilderLogin.buildService(RestApi::class.java)
        retrofit.userLogin(userData).enqueue(
            object : Callback<LoginResp> {
                override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                    //val code = response.code()
                    println(""+response.body())
                    onResult(response.body())

                }
            }
        )
    }//End


    fun registerUser(userData: UserInfoSignUp, onResult: (SignUpResp?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.userRegister(userData).enqueue(
            object : Callback<SignUpResp> {
                override fun onFailure(call: Call<SignUpResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<SignUpResp>, response: Response<SignUpResp>) {
                    //val code = response.code()
                    println("regreg"+response.body())
                    onResult(response.body())
                }
            }
        )
    }//End

    fun paymentUser(userData: PaymentInfo, onResult: (SignUpResp?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.userPayment(userData).enqueue(
            object : Callback<SignUpResp> {
                override fun onFailure(call: Call<SignUpResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<SignUpResp>, response: Response<SignUpResp>) {
                    //val code = response.code()
                    println("regreg"+response.body())
                    onResult(response.body())
                }
            }
        )
    }//End



    fun paymentCheck(userData: PaymentInfo, onResult: (SignUpResp?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.paymentCheck(userData).enqueue(
            object : Callback<SignUpResp> {
                override fun onFailure(call: Call<SignUpResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<SignUpResp>, response: Response<SignUpResp>) {
                    //val code = response.code()
                    println("regreg"+response.body())
                    onResult(response.body())
                }
            }
        )
    }//End

//    fun changepassword(passwordPost: PasswordPost, onResult: (TaskResp?) -> Unit){
//        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
//        retrofit.change_password(passwordPost).enqueue(
//            object : Callback<TaskResp> {
//                override fun onFailure(call: Call<TaskResp>, t: Throwable) {
//                    println("Eroooooooooooooooo"+t.message)
//                    onResult(null)
//                }
//                override fun onResponse( call: Call<TaskResp>, response: Response<TaskResp>) {
//                    //val code = response.code()
//                    println(""+response.body())
//                    onResult(response.body())
//
//                }
//            }
//        )
//    }//End
//
//
//
//
//
//    //We will use this in our recycler View
    fun books(onResult: (BookResp?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.books().enqueue(
            object : Callback<BookResp> {
                override fun onFailure(call: Call<BookResp>, t: Throwable) {
                    println("Eroooooooooooooooo"+t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<BookResp>, response: Response<BookResp>) {
                    //val code = response.code()
                    println("ttttttttttttttt"+response.body())
                    onResult(response.body())

                }
            }
        )
    }


    fun activityBooks(data: ActivityModel,onResult: (BookResp?) -> Unit){
        val retrofit = ServiceBuilderLocal.buildService(RestApi::class.java)
        retrofit.bookActivities(data).enqueue(
            object : Callback<BookResp> {
                override fun onFailure(call: Call<BookResp>, t: Throwable) {
                    println("Erooooo333"+t.message)
                    onResult(null)
                }
                override fun onResponse(call: Call<BookResp>, response: Response<BookResp>) {
                    //val code = response.code()
                    println("Haaaaaaai"+response.body())
                    onResult(response.body())
                }
            }
        )
    }//End
}