package com.paranid5.biatestapp.data.room.chat

import androidx.room.Dao
import androidx.room.Query
import com.paranid5.biatestapp.data.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao : BaseDao<Message> {
    @Query("""
        SELECT * FROM Message WHERE
        (from_user_id = :selfId AND to_user_id = :otherId) OR
        (from_user_id = :otherId AND to_user_id = :selfId)
    """)
    suspend fun getAllMessagesBetweenUsers(selfId: Int, otherId: Int): List<Message>

    @Query("""
        SELECT * FROM Message WHERE
        (from_user_id = :selfId AND to_user_id = :otherId) OR
        (from_user_id = :otherId AND to_user_id = :selfId)
    """)
    fun getAllMessagesBetweenUsersFlow(selfId: Int, otherId: Int): Flow<List<Message>>

    @Query("""
        SELECT * FROM Message WHERE
        (from_user_id = :selfId AND to_user_id = :otherId) OR
        (from_user_id = :otherId AND to_user_id = :selfId)
        ORDER BY send_time DESC
        LIMIT 1
    """)
    suspend fun getLatestMessageBetweenUsers(selfId: Int, otherId: Int): Message?

    @Query("""
        SELECT * FROM Message WHERE
        (from_user_id = :selfId AND to_user_id = :otherId) OR
        (from_user_id = :otherId AND to_user_id = :selfId)
        ORDER BY send_time DESC
        LIMIT 1
    """)
    fun getLatestMessageBetweenUsersFlow(selfId: Int, otherId: Int): Flow<Message?>
}