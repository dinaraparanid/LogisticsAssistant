package com.paranid5.biatestapp.data.room.chat

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.paranid5.biatestapp.data.Message
import com.paranid5.biatestapp.data.retrofit.NetworkMessage
import com.paranid5.biatestapp.data.utils.ext.readLocalDateTime
import com.paranid5.biatestapp.data.utils.ext.writeLocalDateTime
import kotlinx.datetime.LocalDateTime
import com.paranid5.biatestapp.data.room.Entity as BaseEntity

@Entity(
    tableName = "Messages",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("job_id"),
            childColumns = arrayOf("from_user_id")
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("job_id"),
            childColumns = arrayOf("to_user_id")
        )
    ],
    indices = [Index(value = ["from_user_id", "to_user_id", "send_time"], unique = true)]
)
data class DBMessage(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("from_user_id") override val fromUserId: Long,
    @ColumnInfo("to_user_id") override val toUserId: Long,
    override val text: String,
    @ColumnInfo("send_time") override val sendTime: LocalDateTime,
    override val read: Boolean
) : Message, BaseEntity {
    companion object CREATOR : Parcelable.Creator<DBMessage> {
        override fun createFromParcel(parcel: Parcel) = DBMessage(parcel)
        override fun newArray(size: Int) = arrayOfNulls<DBMessage?>(size)
    }

    constructor(networkMessage: NetworkMessage) : this(
        id = 0,
        fromUserId = networkMessage.fromUserId,
        toUserId = networkMessage.toUserId,
        text = networkMessage.text,
        sendTime = networkMessage.sendTime,
        read = networkMessage.read
    )

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        fromUserId = parcel.readLong(),
        toUserId = parcel.readLong(),
        text = parcel.readString() ?: "",
        sendTime = parcel.readLocalDateTime(),
        read = parcel.readInt() > 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(fromUserId)
        parcel.writeLong(toUserId)
        parcel.writeString(text)
        parcel.writeLocalDateTime(sendTime)
        parcel.writeInt(if (read) 1 else 0)
    }

    override fun describeContents() = 0
}
