package com.paranid5.biatestapp.data.room.chat

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(@ApplicationContext context: Context) {
    private companion object {
        private const val DATABASE_NAME = "chat"
    }

    private val db = Room.databaseBuilder(
        context,
        ChatDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val usersDao = db.usersDao()
    private val messagesDao = db.messagesDao()

    // ------------------------ Users ------------------------

    suspend fun getAllContacts(fromUserId: Int) =
        usersDao.getAllContacts(fromUserId)

    fun getAllContactsFlow(fromUserId: Int) =
        usersDao.getAllContactsFlow(fromUserId)

    suspend fun insert(user: User) = usersDao.insert(user)

    suspend fun update(user: User) = usersDao.update(user)

    suspend fun delete(user: User) = usersDao.delete(user)

    // ------------------------ Messages ------------------------

    suspend fun getAllMessagesBetweenUsers(selfId: Int, otherId: Int) =
        messagesDao.getAllMessagesBetweenUsers(selfId, otherId)

    fun getAllMessagesBetweenUsersFlow(selfId: Int, otherId: Int) =
        messagesDao.getAllMessagesBetweenUsersFlow(selfId, otherId)

    suspend fun getLatestMessageBetweenUsers(selfId: Int, otherId: Int) =
        messagesDao.getLatestMessageBetweenUsers(selfId, otherId)

    fun getLatestMessageBetweenUsersFlow(selfId: Int, otherId: Int) =
        messagesDao.getLatestMessageBetweenUsersFlow(selfId, otherId)

    suspend fun insert(message: Message) = messagesDao.insert(message)

    suspend fun update(message: Message) = messagesDao.update(message)

    suspend fun delete(message: Message) = messagesDao.delete(message)
}