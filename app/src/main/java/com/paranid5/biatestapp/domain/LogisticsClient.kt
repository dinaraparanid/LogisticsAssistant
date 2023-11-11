package com.paranid5.biatestapp.domain

import com.paranid5.biatestapp.data.retrofit.NetworkMessage
import com.paranid5.biatestapp.data.retrofit.Employee
import com.paranid5.biatestapp.data.retrofit.Employer
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

// Url placeholder
private const val BIA_LOGISTICS_URL = "https://bia_logistics.ru"

interface BiaLogisticsClient {
    @GET("/users")
    suspend fun getEmployeeByPhoneNumber(@Query("phone_number") phoneNumber: String): Response<Employee>

    @GET("/users")
    suspend fun getEmployeeById(@Query("employee_id") employeeId: Long): Response<Employee>

    @GET("/users/employers")
    suspend fun getEmployerByEmployeeId(@Query("employee_id") employeeId: Long): Response<Employer>

    @GET("/messages")
    suspend fun getMessagesBetweenUsers(
        @Query("employee_id") employeeId: Long,
        @Query("employer_id") employerId: Long,
        @Query("amount") amount: Int? = null
    ): Response<List<NetworkMessage>>

    @POST("/messages")
    suspend fun sendMessageToEmployer(
        @Query("employee_id") employeeId: Long,
        @Query("employer_id") employerId: Long,
        @Query("message") message: String
    ): Response<NetworkMessage>

    @PATCH("/messages")
    suspend fun readMessage(
        @Query("employee_id") employeeId: Long,
        @Query("employer_id") employerId: Long,
        @Query("send_time") sendTime: String
    ): Response<NetworkMessage>
}

val biaLogisticsRetrofit: Retrofit
    get() = Retrofit.Builder()
        .baseUrl(BIA_LOGISTICS_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()