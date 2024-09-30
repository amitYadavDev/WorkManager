package app.exam.workmanagerpractice.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import app.exam.workmanagerpractice.data.local.QuoteDao
import app.exam.workmanagerpractice.data.mappers.toDomain
import app.exam.workmanagerpractice.data.remote.ApiService
import com.google.gson.Gson
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
            Log.i(TAG, "Fetching data...")
            val response = apiService.getQuotes().toDomain(ONE_TIME_WORK_REQUEST)
            quoteDao.insert(response)
            val data = Data.Builder()
                .putString(QUOTE, Gson().toJson(response))
                .build()
            Result.success(data)
        }catch (e: Exception) {
            Result.failure()
        }
    }
}