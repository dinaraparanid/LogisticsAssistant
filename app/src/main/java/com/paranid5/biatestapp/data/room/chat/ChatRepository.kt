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

    suspend fun getUserByJobId(jobId: Long) =
        usersDao.getUserByJobId(jobId)

    suspend fun getAllContacts(fromUserId: Long) =
        usersDao.getAllContacts(fromUserId)

    fun getAllContactsFlow(fromUserId: Long) =
        usersDao.getAllContactsFlow(fromUserId)

    suspend fun insert(vararg users: User) = usersDao.insert(*users)

    suspend fun update(vararg users: User) = usersDao.update(*users)

    suspend fun delete(vararg users: User) = usersDao.delete(*users)

    // ------------------------ Messages ------------------------

    suspend fun getAllMessagesBetweenUsers(selfId: Long, otherId: Long) =
        messagesDao.getAllMessagesBetweenUsers(selfId, otherId)

    fun getAllMessagesBetweenUsersFlow(selfId: Long, otherId: Long) =
        messagesDao.getAllMessagesBetweenUsersFlow(selfId, otherId)

    suspend fun getLatestMessageBetweenUsers(selfId: Long, otherId: Long) =
        messagesDao.getLatestMessageBetweenUsers(selfId, otherId)

    fun getLatestMessageBetweenUsersFlow(selfId: Long, otherId: Long) =
        messagesDao.getLatestMessageBetweenUsersFlow(selfId, otherId)

    suspend fun insert(vararg messages: DBMessage) = messagesDao.insert(*messages)

    suspend fun update(vararg messages: DBMessage) = messagesDao.update(*messages)

    suspend fun delete(vararg messages: DBMessage) = messagesDao.delete(*messages)
}