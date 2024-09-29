package app.exam.workmanagerpractice.data.remote

import app.exam.workmanagerpractice.data.model.QuoteDto
import retrofit2.http.GET

interface ApiService {

    @GET("quotes/random")
    suspend fun getQuotes(): QuoteDto

}