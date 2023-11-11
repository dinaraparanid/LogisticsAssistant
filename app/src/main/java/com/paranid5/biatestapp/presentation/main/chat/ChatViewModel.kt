package com.paranid5.biatestapp.presentation.main.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paranid5.biatestapp.data.Message
import com.paranid5.biatestapp.data.StorageHandler
import com.paranid5.biatestapp.data.retrofit.Employer
import com.paranid5.biatestapp.data.retrofit.NetworkMessage
import com.paranid5.biatestapp.data.room.chat.ChatRepository
import com.paranid5.biatestapp.data.room.chat.DBMessage
import com.paranid5.biatestapp.data.room.chat.User
import com.paranid5.biatestapp.domain.BiaLogisticsClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
        private const val MESSAGE = "message"
    }

    val employeeState = storageHandler.employeeState

    private val _employerState = MutableStateFlow(
        savedStateHandle[EMPLOYER] ?: storageHandler.employerState.value
    )

    val employerState = _employerState.asStateFlow()

    private suspend fun storeEmployer(employer: Employer?) {
        storageHandler.storeEmployer(employer)
        savedStateHandle[EMPLOYER] = _employerState.updateAndGet { employer }
        employer?.let { chatRepository.insert(User(it)) }
    }

    private val networkMessagesState = MutableStateFlow(
        savedStateHandle[NETWORK_MESSAGES] ?: emptyList<NetworkMessage>()
    )

    private fun setNetworkMessages(networkMessages: List<NetworkMessage>) {
        savedStateHandle[NETWORK_MESSAGES] = networkMessagesState.updateAndGet { networkMessages }
    }

    private val dbMessagesState by lazy {
        chatRepository.getAllMessagesBetweenUsersFlow(
            selfId = employeeState.value!!.employeeId,
            otherId = employerState.value!!.employerId
        ).stateIn(viewModelScope, SharingStarted.Eagerly, listOf())
    }

    val messagesState by lazy { dbMessagesState }

    private val _messageState = MutableStateFlow(
        savedStateHandle[MESSAGE] ?: ""
    )

    val messageState = _messageState.asStateFlow()

    fun setMessage(message: String) {
        savedStateHandle[MESSAGE] = _messageState.updateAndGet { message }
    }

    val unreadMessagesState by lazy {
        chatRepository
            .unreadMessagesFlow()
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())
    }

    suspend fun loadEmployer(): Employer? {
        val employee = employeeState.value ?: return null

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

    suspend fun loadMessagesFromNetwork(): Unit? {
        val employee = employeeState.value ?: return null
        val employer = employerState.value ?: return null

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
            setNetworkMessages(msgs)
        }
    }

    suspend fun sendMessage(): Message {
        val employeeId = employeeState.value!!.employeeId
        val employerId = employerState.value!!.employerId
        val text = messageState.value

        val message = runCatching {
            logisticsClient.sendMessageToEmployer(
                employeeId = employeeId,
                employerId = employerId,
                message = text
            ).body()
        }.getOrNull() ?: NetworkMessage(
            fromUserId = employeeId,
            toUserId = employerId,
            text = text,
            sendTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            read = true // В настоящем API этого флага не должно быть, для удобства пусть будет
        )

        val dbMessage = DBMessage(message)
        chatRepository.insert(dbMessage)
        chatRepository.readAllMessages()

        networkMessagesState.update { it + message }

        _messageState.update { "" }
        return message
    }

    suspend fun readMessages(vararg messages: DBMessage) {
        val employeeId = employeeState.value!!.employeeId
        val employerId = employerState.value!!.employerId

        messages.forEach { message ->
            runCatching {
                logisticsClient.readMessage(
                    employeeId = employeeId,
                    employerId = employerId,
                    sendTime = message.sendTime.toString()
                ).body()
            }.getOrNull()
        }

        chatRepository.update(*messages.map { it.copy(read = true) }.toTypedArray())
    }
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