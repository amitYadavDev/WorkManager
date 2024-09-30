package app.exam.workmanagerpractice.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.exam.workmanagerpractice.data.common.formatTimestampToDate
import app.exam.workmanagerpractice.presentation.ui.theme.WorkManagerPracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkManagerPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<QuoteViewModel>()
                    HomeScreen(viewModel) {
                        viewModel.getQuote()
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: QuoteViewModel, fetchQuote: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Today's Quotes")}, actions = {
        IconButton(onClick = { fetchQuote() }) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
        }
    })}) {

        if(uiState.data.isEmpty()) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No data!!")
            }
        } else {
            LazyColumn(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
                items(uiState.data) {quote ->
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = quote.quote)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Author: ${quote.author}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = formatTimestampToDate(quote.time))
                                Text(text = quote.workType)
                            }
                        }
                    }
                }
            }
        }

    }

}
