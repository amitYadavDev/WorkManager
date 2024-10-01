package app.exam.workmanagerpractice.common

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatTimestampToDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/mm/yy", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}