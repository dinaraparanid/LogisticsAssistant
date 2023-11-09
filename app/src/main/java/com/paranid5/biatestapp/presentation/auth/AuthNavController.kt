package com.paranid5.biatestapp.presentation.auth

import androidx.navigation.NavHostController

class AuthNavController(val navController: NavHostController? = null) {
    companion object {
        const val LOGIN_SCREEN = "login"
        const val PASSWORD_SCREEN = "password"
        const val INITIAL_SCREEN = LOGIN_SCREEN
    }

    private fun navigateToScreen(route: String) =
        navController?.navigate(route)
            ?: throw IllegalStateException("AuthNavController was not initialized")

    fun navigateToLoginScreen() = navigateToScreen(LOGIN_SCREEN)
    fun navigateToPasswordScreen() = navigateToScreen(PASSWORD_SCREEN)
}