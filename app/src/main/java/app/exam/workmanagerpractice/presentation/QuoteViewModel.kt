package app.exam.workmanagerpractice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.exam.workmanagerpractice.domain.model.Quote
import app.exam.workmanagerpractice.domain.user_cases.GetAllQuotesFromdbUC
import app.exam.workmanagerpractice.domain.user_cases.GetQuoteUC
import app.exam.workmanagerpractice.domain.user_cases.SetUpPeriodicUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getAllQuotesFromdbUC: GetAllQuotesFromdbUC,
    private val getQuoteUC: GetQuoteUC,
    private val periodicUC: SetUpPeriodicUC
) : ViewModel() {

    val uiState = getAllQuotesFromdbUC.invoke()
        .map { UiState(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState(emptyList()))

    init {
        periodicUC.invoke()
    }

    fun getQuote() = getQuoteUC.invoke()



}

data class UiState(val data: List<Quote>)