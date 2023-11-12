package com.paranid5.biatestapp.presentation.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.paranid5.biatestapp.data.StorageHandler
import com.paranid5.biatestapp.data.retrofit.chat.Employee
import com.paranid5.biatestapp.data.room.chat.ChatRepository
import com.paranid5.biatestapp.data.room.chat.User
import com.paranid5.biatestapp.domain.BiaLogisticsClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val biaLogisticsClient: BiaLogisticsClient,
    private val chatRepository: ChatRepository,
    private val storageHandler: StorageHandler
) : ViewModel() {
    private companion object {
        private const val PHONE_NUMBER = "phone_number"
        private const val PASSWORD = "password"
    }

    private val _phoneNumberState = MutableStateFlow(
        savedStateHandle[PHONE_NUMBER] ?: ""
    )

    val phoneNumberState = _phoneNumberState.asStateFlow()

    fun setPhoneNumber(phoneNumber: String) {
        savedStateHandle[PHONE_NUMBER] = _phoneNumberState.updateAndGet { phoneNumber }
    }

    private val _passwordState = MutableStateFlow(
        savedStateHandle[PASSWORD] ?: ""
    )

    val passwordState = _passwordState.asStateFlow()

    fun setPassword(password: String) {
        savedStateHandle[PASSWORD] = _passwordState.updateAndGet { password }
    }

    private val _employeeState = MutableStateFlow(Employee())

    val userState = _employeeState.asStateFlow()

    suspend fun isPasswordCorrect(): Boolean {
        // Неккоректная base ссылка-заглушка,
        // представим, что мы ничего не видели, и она корректная :)

        val employee = _employeeState.updateAndGet {
            runCatching {
                biaLogisticsClient.getEmployeeByPhoneNumber(
                    phoneNumber = _phoneNumberState.value
                ).body()
            }.getOrNull() ?: Employee(phoneNumber = _phoneNumberState.value) // Типа вернул юзера
        }

        storeEmployee(employee)

        val password = employee.password
        return _passwordState.value == password
    }

    private suspend fun storeEmployee(employee: Employee) {
        storageHandler.storeEmployee(employee)
        chatRepository.insert(User(employee))
    }
}