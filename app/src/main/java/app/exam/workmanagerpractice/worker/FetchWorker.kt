package app.exam.workmanagerpractice.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.exam.workmanagerpractice.data.local.QuoteDao
import app.exam.workmanagerpractice.data.mappers.toDomain
import app.exam.workmanagerpractice.data.remote.ApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val ONE_TIME_WORK_REQUEST = "ONE_TIME_WORK_REQUEST"

@HiltWorker
class FetchWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val apiService: ApiService,
    private val quoteDao: QuoteDao
) : CoroutineWorker(context, workerParameters) {
    private val TAG = "FetchWorker"


    override suspend fun doWork(): Result {
        return try {
            val response = apiService.getQuotes().toDomain(ONE_TIME_WORK_REQUEST)
            quoteDao.insert(response)
            Log.i(TAG, response.toString())
            Result.success()
        }catch (e: Exception) {
            Result.failure()
        }
    }
}