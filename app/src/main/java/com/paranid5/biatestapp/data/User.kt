package com.paranid5.biatestapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String = "Савченко Арсений Дмитриевич",
    val password: String = "101010",
    @SerializedName("portrait_url") val portraitUrl: String = "https://avatars.githubusercontent.com/u/58735614",
    val job: String = "Разработчик",
    @SerializedName("employee_id") val employeeId: Long = 1234,
    @SerializedName("phone_number") val phoneNumber: String = "+7 800 555-35-35",
    val citizenship: String = "РФ",
    val car: String = "Мерседес",
    @SerializedName("car_number") val carNumber: String = "А 000 АА 199"
) : Parcelable
