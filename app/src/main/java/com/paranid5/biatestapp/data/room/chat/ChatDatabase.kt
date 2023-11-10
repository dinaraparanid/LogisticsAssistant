package com.paranid5.biatestapp.data.room.chat

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paranid5.biatestapp.data.room.Converters

@Database(
    entities = [User::class, Message::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun messagesDao(): MessagesDao
}