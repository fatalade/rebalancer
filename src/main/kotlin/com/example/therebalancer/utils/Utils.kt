package com.example.therebalancer

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getDateFromString(stringDate: String) = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US))
fun getAgeFromDOB(dob: LocalDate) = Period.between(dob,LocalDate.now()).years
fun getYearsToRetirement(dob: LocalDate, retirementAge: Int) = (retirementAge - getAgeFromDOB(dob)).coerceAtLeast(0)