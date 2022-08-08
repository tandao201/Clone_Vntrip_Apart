package com.tan.login.FormatString

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class CurTime {
	companion object {
		var dayOfWeeks = listOf<String>("SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY")

		fun getDay(curDay: String): String {

			return when (curDay) {
				"MONDAY" -> "Thứ Hai"
				"TUESDAY" -> "Thứ Ba"
				"WEDNESDAY" -> "Thứ Tư"
				"THURSDAY" -> "Thứ Năm"
				"FRIDAY" -> "Thứ Sáu"
				"SATURDAY" -> "Thứ Bảy"
				"SUNDAY" -> "Chủ Nhật"
				else -> "LOI"
			}
		}

		fun getDateTime(curDay: String,curDate:LocalDate) : String {
			var curDay = getDay(curDay)
			var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
			var curDateFormat = curDate.format(formatter)
			return "${curDay} - ${curDateFormat}"
		}

		fun getDateTimeString(curDay: String,curDate:String) : String {
			var curDay = getDay(curDay)
			return "${curDay} - ${curDate}"
		}

		fun convertLongToTime(time: Long): String {
			val date = Date(time)
			val format = SimpleDateFormat("dd/MM/yyyy")
			return format.format(date)
		}

		fun convertLongtoDayofWeek(time: Long): String{
			val c = Calendar.getInstance()
			val date = convertLongToTime(time)
			val format = SimpleDateFormat("dd/MM/yyyy")
			var dateObject : Date = format.parse(date)
			c.time = dateObject // yourdate is an object of type Date

			val dayOfWeek = c[Calendar.DAY_OF_WEEK]
			return dayOfWeeks[dayOfWeek-1]
		}

		fun getTimeFromString(time: String): Date {
			val date = time.split(" ")[3].trim()
			val format = SimpleDateFormat("dd/MM/yyyy")
			return format.parse(date)
		}

		fun dateRange(begin:String, end:String) :Int{
			var formatter = SimpleDateFormat("dd/MM/yyyy")
			var beginDate = formatter.parse(getDateFromTv(begin))
			var endDate = formatter.parse(getDateFromTv(end))

			var diff: Long = endDate.time - beginDate.time
			var days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)
			return days.toInt()
		}

		fun getDateFromTv(input: String): String {
			return input.split(" ")[3]
		}

		fun formatCheckInDate(checkIn: String): String {
			var formatter1 = SimpleDateFormat("dd/MM/yyyy")
			var formatter2 = SimpleDateFormat("yyyyMMdd")
			var date = formatter1.parse(getDateFromTv(checkIn))
			return formatter2.format(date)
		}

		fun getDayAndMonth(date: String): String {
			return date.split(" ")[3].substring(0,5)
		}

		fun getHourGoAnTo (time1: String,time2: String): MutableList<String> {
			var result = mutableListOf<String>()
			result.add(time1.substring(11,16))
			result.add(time2.substring(11,16))
			return result
		}

		fun getRangeOfTwoHour(time1: String, time2: String): String {
			val formatter = SimpleDateFormat("HH:mm")
			val date1 = formatter.parse(time1)
			val date2 = formatter.parse(time2)

			var diff = date2.time-date1.time
			if (diff < 0) diff += 86400000
			val hours = TimeUnit.MILLISECONDS.toHours(diff)
			val minutesInt = TimeUnit.MILLISECONDS.toMinutes(diff)-60*hours
			var minutes = minutesInt.toString()
			if (minutesInt < 10)
				minutes = "0$minutes"
			return "${hours}h${minutes}"
		}
	}
}