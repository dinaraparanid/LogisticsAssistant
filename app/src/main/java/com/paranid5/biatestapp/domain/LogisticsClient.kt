package com.paranid5.biatestapp.domain

import com.paranid5.biatestapp.data.retrofit.chat.Employee
import com.paranid5.biatestapp.data.retrofit.chat.Employer
import com.paranid5.biatestapp.data.retrofit.chat.NetworkMessage
import com.paranid5.biatestapp.data.retrofit.tasks.Task
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

// Url placeholder
private const val BIA_LOGISTICS_URL = "https://bia_logistics.ru"

interface BiaLogisticsClient {

    // ---------------------------- Users ----------------------------

    @GET("/users")
    suspend fun getEmployeeByPhoneNumber(@Query("phone_number") phoneNumber: String): Response<Employee>

    @GET("/users")
    suspend fun getEmployeeById(@Query("employee_id") employeeId: Long): Response<Employee>

    @GET("/users/employers")
    suspend fun getEmployerByEmployeeId(@Query("employee_id") employeeId: Long): Response<Employer>

    // ---------------------------- Chat ----------------------------

    @GET("/chat")
    suspend fun getMessagesBetweenUsers(
        @Query("employee_id") employeeId: Long,
        @Query("employer_id") employerId: Long,
        @Query("amount") amount: Int? = null
    ): Response<List<NetworkMessage>>

    @POST("/chat")
    suspend fun sendMessageToEmployer(
        @Query("employee_id") employeeId: Long,
        @Query("employer_id") employerId: Long,
        @Query("message") message: String
    ): Response<NetworkMessage>

    @PATCH("/chat")
    suspend fun readMessage(
        @Query("employee_id") employeeId: Long,
        @Query("employer_id") employerId: Long,
        @Query("send_time") sendTime: String
    ): Response<NetworkMessage>

    // ---------------------------- Tasks ----------------------------

    @GET("/tasks/incoming")
    suspend fun getIncomingTasks(): Response<List<Task>>

    @GET("/tasks/executing")
    suspend fun getExecutingTasks(): Response<List<Task>>

    @POST("/tasks")
    suspend fun acceptOrDeclineTask(
        @Query("task_id") taskId: Long,
        @Query("employee_id") employeeId: Long,
        @Query("accept") accept: Boolean
    ): Response<String>

    @PATCH("/tasks/status")
    suspend fun updateDestinationTaskStatus(
        @Query("destination_task_id") destinationTaskId: Long,
        @Query("employee_id") employeeId: Long,
        @Query("status") status: String
    ): Response<String>

    @Multipart
    @POST("/tasks/status")
    suspend fun complainAboutIncident(
        @Query("task_id") taskId: Long,
        @Query("employee_id") employeeId: Long,
        @Query("incident_description") incidentDescription: String,
        @Part incidentImages: Array<MultipartBody.Part>
    ): Response<String>
}

val biaLogisticsRetrofit: Retrofit
    get() = Retrofit.Builder()
        .baseUrl(BIA_LOGISTICS_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()