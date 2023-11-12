package com.paranid5.biatestapp.data.retrofit.chat

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.paranid5.biatestapp.data.Message
import com.paranid5.biatestapp.data.utils.ext.readLocalDateTime
import com.paranid5.biatestapp.data.utils.ext.writeLocalDateTime
import kotlinx.datetime.LocalDateTime

data class NetworkMessage(
    @SerializedName("from_user_id") override val fromUserId: Long,
    @SerializedName("to_user_id") override val toUserId: Long,
    override val text: String,
    @SerializedName("send_time") override val sendTime: LocalDateTime,
    override val read: Boolean
) : Message {
    companion object CREATOR : Parcelable.Creator<NetworkMessage> {
        override fun createFromParcel(parcel: Parcel) = NetworkMessage(parcel)
        override fun newArray(size: Int) = arrayOfNulls<NetworkMessage?>(size)
    }

    constructor(parcel: Parcel) : this(
        fromUserId = parcel.readLong(),
        toUserId = parcel.readLong(),
        text = parcel.readString() ?: "",
        sendTime = parcel.readLocalDateTime(),
        read = parcel.readInt() > 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(fromUserId)
        parcel.writeLong(toUserId)
        parcel.writeString(text)
        parcel.writeLocalDateTime(sendTime)
        parcel.writeInt(if (read) 1 else 0)
    }

    override fun describeContents() = 0
}