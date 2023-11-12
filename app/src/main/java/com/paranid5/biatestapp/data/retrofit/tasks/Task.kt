package com.paranid5.biatestapp.data.retrofit.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    @SerializedName("task_id") val taskId: Long,
    val title: String,
    val status: String,
    val price: String,
    @SerializedName("cargo_type") val cargoType: String,
    @SerializedName("task_city") val taskCity: String,
    @SerializedName("order_date_time") val orderDateTime: OrderDateTime,
    @SerializedName("car_body_type") val carBodyType: String,
    @SerializedName("cargo_weight") val cargoWeight: String,
    @SerializedName("load_capacity") val loadCapacity: String,
    @SerializedName("loading_type") val loadingType: String,
    @SerializedName("medical_book") val medicalBook: Boolean,
    @SerializedName("order_details") val orderDetails: String,
    val contacts: List<Contact>,
    @SerializedName("destination_points") val destinationPoints: List<Destination>,
    @SerializedName("client_rules") val clientRulesUrl: String,
) : Parcelable, Comparable<Task> {
    override fun compareTo(other: Task) = orderDateTime.compareTo(other.orderDateTime)
}
