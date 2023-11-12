package com.paranid5.biatestapp.data.retrofit.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    @SerializedName("destination_task_id") val destinationTaskId: Long,
    @SerializedName("task_type") val taskType: String,
    @SerializedName("start_location") val startLocation: String,
    @SerializedName("loading_time") val loadingTime: OrderDateTime,
    val company: String,
    val contact: Contact,
    val commentary: String,
) : Parcelable
