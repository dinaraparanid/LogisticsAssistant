package com.paranid5.biatestapp.data.room.chat

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.paranid5.biatestapp.data.retrofit.Employee
import com.paranid5.biatestapp.data.retrofit.Employer
import kotlinx.parcelize.Parcelize
import com.paranid5.biatestapp.data.room.Entity as BaseEntity

@Parcelize
@Entity(
    tableName = "Users",
    indices = [Index(value = ["job_id"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo("avatar_url") val avatarUrl: String,
    @ColumnInfo("job_id") val jobId: Long,
    @ColumnInfo("phone_number") val phoneNumber: String,
) : Parcelable, BaseEntity {
    constructor(employee: Employee) : this(
        id = 0,
        name = employee.name,
        avatarUrl = employee.avatarUrl,
        jobId = employee.employeeId,
        phoneNumber = employee.phoneNumber
    )

    constructor(employer: Employer) : this(
        id = 0,
        name = employer.name,
        avatarUrl = employer.avatarUrl,
        jobId = employer.employerId,
        phoneNumber = employer.phoneNumber
    )
}
