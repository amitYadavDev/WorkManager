package app.exam.workmanagerpractice.domain.user_cases

import app.exam.workmanagerpractice.domain.repository.QuoteRepository
import javax.inject.Inject

class SetUpPeriodicUC @Inject constructor(private val quoteRepository: QuoteRepository) {

    operator fun invoke() = quoteRepository.setPeriodicWorkRequest()
}