package com.paranid5.biatestapp.presentation.main.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.paranid5.biatestapp.data.StorageHandler
import com.paranid5.biatestapp.data.retrofit.Employer
import com.paranid5.biatestapp.data.retrofit.NetworkMessage
import com.paranid5.biatestapp.data.room.chat.ChatRepository
import com.paranid5.biatestapp.data.room.chat.DBMessage
import com.paranid5.biatestapp.data.room.chat.User
import com.paranid5.biatestapp.domain.BiaLogisticsClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val logisticsClient: BiaLogisticsClient,
    private val chatRepository: ChatRepository,
    private val storageHandler: StorageHandler
) : ViewModel() {
    private companion object {
        private const val EMPLOYER = "employer"
        private const val NETWORK_MESSAGES = "network_msg"
        private const val DB_MESSAGES = "db_msg"
    }

    private val _employerState = MutableStateFlow(
        savedStateHandle[EMPLOYER] ?: storageHandler.employerState.value
    )

    val employerState = _employerState.asStateFlow()

    private suspend fun storeEmployer(employer: Employer?) {
        storageHandler.storeEmployer(employer)
        savedStateHandle[EMPLOYER] = _employerState.updateAndGet { employer }
        employer?.let { chatRepository.insert(User(it)) }
    }

    private val _networkMessagesState = MutableStateFlow(
        savedStateHandle[NETWORK_MESSAGES] ?: emptyList<NetworkMessage>()
    )

    val networkMessagesState = _networkMessagesState.asStateFlow()

    fun setNetworkMessages(networkMessages: List<NetworkMessage>) {
        savedStateHandle[NETWORK_MESSAGES] = _networkMessagesState.updateAndGet { networkMessages }
    }

    private val _dbMessagesState = MutableStateFlow(
        savedStateHandle[DB_MESSAGES] ?: emptyList<DBMessage>()
    )

    val dbMessagesState = _dbMessagesState.asStateFlow()

    fun setDbMessages(dbMessages: List<DBMessage>) {
        savedStateHandle[DB_MESSAGES] = _dbMessagesState.updateAndGet { dbMessages }
    }

    suspend fun loadEmployer(): Employer? {
        val employee = storageHandler.employeeState.value ?: return null

        // Как будто получили работодателя

        val employer = runCatching {
            logisticsClient.getEmployerByEmployeeId(
                employeeId = employee.employeeId
            ).body()
        }
            .getOrNull()
            ?: employerState.value
            ?: Employer()

        storeEmployer(employer)
        return employer
    }

    private suspend fun loadMessagesFromNetwork(): List<NetworkMessage>? {
        val employee = storageHandler.employeeState.value ?: return emptyList()
        val employer = storageHandler.employerState.value ?: return emptyList()

        // Как будто получили список сообщений

        val messages = runCatching {
//            logisticsClient.getMessagesBetweenUsers(
//                employeeId = employee.employeeId,
//                employerId = employer.employerId
//            ).body()

            initialMessagesPlaceholder(
                employeeId = employee.employeeId,
                employerId = employer.employerId
            )
        }.getOrNull()

        return messages?.let { msgs ->
            chatRepository.insert(*msgs.map(::DBMessage).toTypedArray())
            _networkMessagesState.updateAndGet { msgs }
        }
    }

    private suspend fun loadMessagesFromDB(): List<DBMessage> {
        val employee = storageHandler.employeeState.value ?: return emptyList()
        val employer = storageHandler.employerState.value ?: return emptyList()

        return _dbMessagesState.updateAndGet {
            chatRepository.getAllMessagesBetweenUsers(
                selfId = employee.employeeId,
                otherId = employer.employerId
            )
        }
    }

    suspend fun loadMessages() = loadMessagesFromNetwork() ?: loadMessagesFromDB()
}

private fun initialMessagesPlaceholder(employeeId: Long, employerId: Long) = listOf(
    NetworkMessage(
        fromUserId = employerId,
        toUserId = employeeId,
        text = "Ты сегодня выйдешь на смену?",
        sendTime = LocalDateTime(
            year = 2023,
            monthNumber = 10,
            dayOfMonth = 15,
            hour = 23,
            minute = 48,
            second = 0
        ),
        read = true
    ),
    NetworkMessage(
        fromUserId = employeeId,
        toUserId = employerId,
        text = "Да, сегодня буду",
        sendTime = LocalDateTime(
            year = 2023,
            monthNumber = 10,
            dayOfMonth = 15,
            hour = 23,
            minute = 48,
            second = 20
        ),
        read = true
    ),
    NetworkMessage(
        fromUserId = employerId,
        toUserId = employeeId,
        text = "Ждем тебя",
        sendTime = LocalDateTime(
            year = 2023,
            monthNumber = 10,
            dayOfMonth = 15,
            hour = 23,
            minute = 48,
            second = 40
        ),
        read = false
    )
)