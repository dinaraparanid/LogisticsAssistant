package com.paranid5.biatestapp.data.room.chat

import androidx.room.Dao
import androidx.room.Query
import com.paranid5.biatestapp.data.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao : BaseDao<DBMessage> {
    @Query(
        """
        SELECT *
        FROM Messages
        WHERE
            (from_user_id = :selfId AND to_user_id = :otherId) OR
            (from_user_id = :otherId AND to_user_id = :selfId)
        ORDER BY send_time
    """
    )
    suspend fun getAllMessagesBetweenUsers(selfId: Long, otherId: Long): List<DBMessage>

    @Query(
        """
        SELECT *
        FROM Messages
        WHERE
            (from_user_id = :selfId AND to_user_id = :otherId) OR
            (from_user_id = :otherId AND to_user_id = :selfId)
        ORDER BY send_time
    """
    )
    fun getAllMessagesBetweenUsersFlow(selfId: Long, otherId: Long): Flow<List<DBMessage>>

    @Query(
        """
        SELECT *
        FROM Messages
        WHERE
            (from_user_id = :selfId AND to_user_id = :otherId) OR
            (from_user_id = :otherId AND to_user_id = :selfId)
        ORDER BY send_time DESC
        LIMIT 1
    """
    )
    suspend fun getLatestMessageBetweenUsers(selfId: Long, otherId: Long): DBMessage?

    @Query(
        """
        SELECT *
        FROM Messages
        WHERE
            (from_user_id = :selfId AND to_user_id = :otherId) OR
            (from_user_id = :otherId AND to_user_id = :selfId)
        ORDER BY send_time DESC
        LIMIT 1
    """
    )
    fun getLatestMessageBetweenUsersFlow(selfId: Long, otherId: Long): Flow<DBMessage?>
}