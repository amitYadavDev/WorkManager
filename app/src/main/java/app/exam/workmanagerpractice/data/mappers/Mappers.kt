package app.exam.workmanagerpractice.data.mappers

import app.exam.workmanagerpractice.data.model.QuoteDto
import app.exam.workmanagerpractice.domain.model.Quote

fun QuoteDto.toDomain(workType: String): Quote {
    return Quote(author, id, quote, workType)
}