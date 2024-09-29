package app.exam.workmanagerpractice.domain.user_cases

import app.exam.workmanagerpractice.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getQuote()

    fun getAllQuotes(): Flow<List<Quote>>

    fun setPeriodicWorkRequest()
}