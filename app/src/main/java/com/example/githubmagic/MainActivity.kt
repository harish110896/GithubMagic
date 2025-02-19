package com.example.githubmagic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubmagic.presentation.viewmodel.GithubViewModel
import com.example.githubmagic.ui.theme.GithubMagicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ItemList(

            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: GithubViewModel = hiltViewModel()) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ItemList(viewModel: GithubViewModel = hiltViewModel()) {
    val items = viewModel.posts.collectAsLazyPagingItems()

    LazyColumn {
        items(items.itemCount) { index ->
            items[index]?.let { item ->
                Text(
                    text = item.title.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        // Show Loading Indicator
        items.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                }

                loadState.append is LoadState.Error -> {
                    item { Text("Error loading more data") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GithubMagicTheme {
        Greeting("Android")
    }
}