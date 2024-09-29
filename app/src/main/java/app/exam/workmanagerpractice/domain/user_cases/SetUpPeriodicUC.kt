package app.exam.workmanagerpractice.domain.user_cases

import javax.inject.Inject

class SetUpPeriodicUC @Inject constructor(private val quoteRepository: QuoteRepository) {

    operator fun invoke() = quoteRepository.setPeriodicWorkRequest()
}