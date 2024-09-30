package app.exam.workmanagerpractice.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.exam.workmanagerpractice.CHANNEL
import app.exam.workmanagerpractice.R
import app.exam.workmanagerpractice.data.local.QuoteDao
import app.exam.workmanagerpractice.data.mappers.toDomain
import app.exam.workmanagerpractice.data.remote.ApiService
import app.exam.workmanagerpractice.domain.model.Quote
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val PERIODIC_WORK_REQUEST = "PERIODIC_WORK_REQUEST"

@HiltWorker
class PeriodicWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val apiService: ApiService,
    private val quoteDao: QuoteDao
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        return try {
            val response = apiService.getQuotes().toDomain(PERIODIC_WORK_REQUEST)
            quoteDao.insert(response)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(ContextCompat.checkSelfPermission(context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED) {
                    val notification = NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Today's Quote!!")
                        .setContentText(response.quote.plus("${response.author}"))
                        .build()
                    NotificationManagerCompat.from(context).notify(1, notification)
                }
            }

            Result.success()
        }catch (e: Exception) {
            Result.failure()
        }
    }
}
