package com.paranid5.biatestapp.data.room.chat

import androidx.room.Dao
import androidx.room.Query
import com.paranid5.biatestapp.data.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao : BaseDao<User> {
    @Query("""
        SELECT * FROM User WHERE id = (
            SELECT to_user_id FROM Message WHERE from_user_id = :fromUserId
        )
    """)
    suspend fun getAllContacts(fromUserId: Int): List<User>

    @Query("""
        SELECT * FROM User WHERE id = (
            SELECT to_user_id FROM Message WHERE from_user_id = :fromUserId
        )
    """)
    fun getAllContactsFlow(fromUserId: Int): Flow<List<User>>
}