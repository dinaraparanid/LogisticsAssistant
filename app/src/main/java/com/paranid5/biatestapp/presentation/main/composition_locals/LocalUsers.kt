package com.paranid5.biatestapp.presentation.main.composition_locals

import androidx.compose.runtime.compositionLocalOf
import com.paranid5.biatestapp.data.retrofit.chat.Employee
import com.paranid5.biatestapp.data.retrofit.chat.Employer

val LocalEmployee = compositionLocalOf { Employee() }

val LocalEmployer = compositionLocalOf { Employer() }