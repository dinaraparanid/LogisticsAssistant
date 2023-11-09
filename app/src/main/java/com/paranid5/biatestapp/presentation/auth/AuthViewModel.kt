package com.paranid5.biatestapp.presentation.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.paranid5.biatestapp.data.User
import com.paranid5.biatestapp.domain.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authClient: AuthClient
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

    private val _userState = MutableStateFlow(User())

    val userState = _userState.asStateFlow()

    suspend fun isPasswordCorrect(): Boolean {
        // Неккоректная base ссылка-заглушка,
        // представим, что мы ничего не видели, и она корректная :)

        val user = _userState.updateAndGet {
            runCatching {
                authClient.getUserPasswordByPhoneNumber(
                    phoneNumber = _phoneNumberState.value
                ).body()
            }.getOrNull() ?: User(password = "101010") // Типа корректный пароль
        }

        val password = user.password

        return _passwordState.value == password
    }
}