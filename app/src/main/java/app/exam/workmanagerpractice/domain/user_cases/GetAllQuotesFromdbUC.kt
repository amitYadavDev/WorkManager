package app.exam.workmanagerpractice.domain.user_cases

import javax.inject.Inject

class GetAllQuotesFromdbUC @Inject constructor(private val quoteRepository: QuoteRepository) {

    operator fun invoke() = quoteRepository.getAllQuotes()
}