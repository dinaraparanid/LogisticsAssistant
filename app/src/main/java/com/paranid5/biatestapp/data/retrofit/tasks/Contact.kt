package com.paranid5.biatestapp.data.retrofit.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    @SerializedName("contact_person") val contactPerson: String,
    @SerializedName("phone_number") val phoneNumber: String
) : Parcelable
