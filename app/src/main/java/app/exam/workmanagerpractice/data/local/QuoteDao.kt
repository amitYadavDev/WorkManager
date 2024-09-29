package app.exam.workmanagerpractice.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.exam.workmanagerpractice.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote)


    @Query("SELECT * FROM Quote ORDER BY time DESC")
    fun getAllQuotes(): Flow<List<Quote>>

}