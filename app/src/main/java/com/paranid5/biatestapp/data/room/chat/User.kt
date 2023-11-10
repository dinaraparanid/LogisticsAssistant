package com.paranid5.biatestapp.data.room.chat

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import com.paranid5.biatestapp.data.room.Entity as BaseEntity

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val password: String,
    @ColumnInfo("portrait_url") val portraitUrl: String,
    val job: String,
    @ColumnInfo("employee_id") val employeeId: Long,
    @ColumnInfo("phone_number") val phoneNumber: String,
    val citizenship: String,
    val car: String,
    @ColumnInfo("car_number") val carNumber: String
) : Parcelable, BaseEntity
