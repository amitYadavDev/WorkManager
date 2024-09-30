package app.exam.workmanagerpractice.worker

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.exam.workmanagerpractice.CHANNEL
import app.exam.workmanagerpractice.R
import app.exam.workmanagerpractice.domain.model.Quote
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val QUOTE = "quote"

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(context, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                val quoteJson = workerParameters.inputData?.getString(QUOTE)
                val quote = Gson().fromJson<Quote>(quoteJson, Quote::class.java)
                val notification = NotificationCompat.Builder(context, CHANNEL)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Today's Quote!!")
                    .setContentText(quote.quote.plus("${quote.author}"))
                    .build()
                NotificationManagerCompat.from(context).notify(1, notification)
            }
        }

        return Result.success()
    }

}