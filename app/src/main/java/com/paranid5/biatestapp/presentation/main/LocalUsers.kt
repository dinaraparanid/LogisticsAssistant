package com.paranid5.biatestapp.presentation.main

import androidx.compose.runtime.compositionLocalOf
import com.paranid5.biatestapp.data.retrofit.Employee
import com.paranid5.biatestapp.data.retrofit.Employer

val LocalEmployee = compositionLocalOf { Employee() }

val LocalEmployer = compositionLocalOf { Employer() }