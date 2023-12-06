package com.dimovsoft.shutterfly.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val SIMPLE_DATE_PATTERN = "yyyy-MM-dd"
const val DATE_IS_MISSING = "N/A"

fun String?.extractYear(): String {
	if (this == null) {
		return DATE_IS_MISSING
	}

	val formatter = DateTimeFormatter.ofPattern(SIMPLE_DATE_PATTERN)
	val date = LocalDate.parse(this, formatter)
	return date.year.toString()
}