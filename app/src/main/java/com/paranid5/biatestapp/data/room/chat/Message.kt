package com.paranid5.biatestapp.data.room.chat

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.paranid5.biatestapp.data.utils.ext.readLocalDateTime
import com.paranid5.biatestapp.data.utils.ext.writeLocalDateTime
import kotlinx.datetime.LocalDateTime
import com.paranid5.biatestapp.data.room.Entity as BaseEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("from_user_id")
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("to_user_id")
        )
    ]
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("from_user_id") val fromUserId: Int,
    @ColumnInfo("to_user_id") val toUserId: Int,
    val message: String,
    @ColumnInfo("send_time") val sendTime: LocalDateTime
) : BaseEntity, Parcelable {
    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel) = Message(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Message?>(size)
    }

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        fromUserId = parcel.readInt(),
        toUserId = parcel.readInt(),
        message = parcel.readString() ?: "",
        sendTime = parcel.readLocalDateTime()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(fromUserId)
        parcel.writeInt(toUserId)
        parcel.writeString(message)
        parcel.writeLocalDateTime(sendTime)
    }

    override fun describeContents() = 0
}
