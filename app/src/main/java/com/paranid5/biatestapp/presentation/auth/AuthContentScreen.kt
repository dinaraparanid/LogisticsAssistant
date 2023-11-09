package com.paranid5.biatestapp.presentation.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AuthContentScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
) {
    val authNavController = LocalAuthNavController.current

    NavHost(
        modifier = modifier,
        navController = authNavController.navController!!,
        startDestination = AuthNavController.INITIAL_SCREEN
    ) {
        composable(AuthNavController.LOGIN_SCREEN) {
            PhoneLoginScreen(authViewModel = authViewModel, modifier = Modifier.fillMaxSize())
        }

        composable(AuthNavController.PASSWORD_SCREEN) {
            PasswordScreen(authViewModel = authViewModel, modifier = Modifier.fillMaxSize())
        }
    }
}