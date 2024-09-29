package app.exam.workmanagerpractice.data.repository

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import app.exam.workmanagerpractice.data.local.QuoteDao
import app.exam.workmanagerpractice.domain.model.Quote
import app.exam.workmanagerpractice.domain.repository.QuoteRepository
import app.exam.workmanagerpractice.worker.FetchWorker
import app.exam.workmanagerpractice.worker.PeriodicWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class QuoteRepoImpl(private val workManager: WorkManager,
    private val quoteDao: QuoteDao) : QuoteRepository {
    override fun getQuote() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()



        val workRequest = OneTimeWorkRequestBuilder<FetchWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueue(workRequest)
    }

    override fun getAllQuotes(): Flow<List<Quote>> {
        return quoteDao.getAllQuotes()
    }

    override fun setPeriodicWorkRequest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(PeriodicWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork("uniqueWorkName",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest)
    }
}