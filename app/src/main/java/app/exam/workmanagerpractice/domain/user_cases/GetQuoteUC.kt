package app.exam.workmanagerpractice.domain.user_cases

import javax.inject.Inject

class GetQuoteUC @Inject constructor(private val repository: QuoteRepository) {

    operator fun invoke() = repository.getQuote()
}