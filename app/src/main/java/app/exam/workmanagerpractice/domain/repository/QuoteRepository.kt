package app.exam.workmanagerpractice.domain.repository

import app.exam.workmanagerpractice.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getQuote()

    fun getAllQuotes(): Flow<List<Quote>>

    fun setPeriodicWorkRequest()
}