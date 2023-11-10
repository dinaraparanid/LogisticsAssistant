package com.paranid5.biatestapp.data.room.chat

import androidx.room.Dao
import androidx.room.Query
import com.paranid5.biatestapp.data.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao : BaseDao<User> {
    @Query("SELECT * FROM Users WHERE job_id = :jobId")
    suspend fun getUserByJobId(jobId: Long): User?

    @Query(
        """
        SELECT *
        FROM Users
        WHERE job_id = (
            SELECT to_user_id
            FROM Messages
            WHERE from_user_id = :fromUserId
        )
    """
    )
    suspend fun getAllContacts(fromUserId: Long): List<User>

    @Query(
        """
        SELECT *
        FROM Users
        WHERE job_id = (
            SELECT to_user_id
            FROM Messages
            WHERE from_user_id = :fromUserId
        )
    """
    )
    fun getAllContactsFlow(fromUserId: Long): Flow<List<User>>
}