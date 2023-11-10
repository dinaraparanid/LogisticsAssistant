package com.paranid5.biatestapp.data.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employer(
    val name: String = "Торвальдс Линус Бенедикт",
    @SerializedName("avatar_url") val avatarUrl: String = "https://avatars.githubusercontent.com/u/1024025",
    @SerializedName("employer_id") val employerId: Long = 5678,
    @SerializedName("phone_number") val phoneNumber: String = "+7 800 555-35-35"
) : Parcelable