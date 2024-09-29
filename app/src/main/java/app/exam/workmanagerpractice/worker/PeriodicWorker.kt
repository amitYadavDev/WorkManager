package app.exam.workmanagerpractice.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.exam.workmanagerpractice.data.local.QuoteDao
import app.exam.workmanagerpractice.data.mappers.toDomain
import app.exam.workmanagerpractice.data.remote.ApiService
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
            Result.success()
        }catch (e: Exception) {
            Result.failure()
        }
    }
}
