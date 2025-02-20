package com.example.githubmagic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.githubmagic.data.model.PrInfoDto
import com.example.githubmagic.presentation.viewmodel.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubRepos()
        }
    }
}

@Composable
fun GithubRepos() {

    val viewModel: GithubViewModel = hiltViewModel()

    val prList = viewModel.posts.collectAsLazyPagingItems()

    LazyColumn {
        items(prList.itemCount) { index ->
            prList[index]?.let { prInfo ->
                PrInfoRow(prInfo)
            }
        }

        // Show Loading Indicator
        prList.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                }

                is LoadState.Error -> {
                    item { Text("Error loading more data") }
                }

                is LoadState.NotLoading -> { //NO - OP}
                }
            }
        }
    }
}


@Composable
fun PrInfoRow(prInfo: PrInfo) {
    // Memoizing formatted dates to avoid recomputation
    val createdDate = remember(prInfo.createdAt) { formatDate(prInfo.createdAt) }
    val mergedDate = remember(prInfo.mergedAt) { formatDate(prInfo.mergedAt) }
    val closedDate = remember(prInfo.closedAt) { formatDate(prInfo.closedAt) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Left-side user avatar
        AsyncImage(
            model = prInfo.user?.avatarUrl,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )

        // Right-side text section
        Column(modifier = Modifier.weight(1f)) {
            // PR Title (Normal weight)
            Text(
                text = prInfo.title ?: "No Title",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )

            // First Subtitle: "by username on createdDate"
            Text(
                fontSize = 14.sp,
                color = Color.Gray,
                text = buildAnnotatedString {
                    append("by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                        append(prInfo.user?.login ?: "Unknown") // Bold Username
                    }
                    append(" on ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                        append(createdDate) // Bold Created Date
                    }
                }
            )

            // Second Subtitle: "Merged/Closed on date"
            prInfo.mergedAt?.let {
                Text(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    text = buildAnnotatedString {
                        append("Merged on ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                            append(mergedDate) // Bold Merged Date
                        }
                    }
                )
            } ?: prInfo.closedAt?.let {
                Text(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    text = buildAnnotatedString {
                        append("Closed on ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                            append(closedDate) // Bold Closed Date
                        }
                    }
                )
            }
        }
    }
}

data class PrInfo(
    val id: Long?,
    val title: String?,
    val createdAt: Date?,
    val closedAt: Date?,
    val mergedAt: Date?,
    val user: User?
)

data class User(
    val id: Long?,
    val login: String?,
    val avatarUrl: String?
)

// Helper function to format dates
fun formatDate(date: Date?): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return date?.let { formatter.format(it) } ?: "N/A"
}

fun PrInfoDto.toPrInfo(): PrInfo {
    return PrInfo(
        id = this.id,
        title = this.title,
        createdAt = Util.convertToLocalTime(this.created_at),
        closedAt = Util.convertToLocalTime(this.closed_at),
        mergedAt = Util.convertToLocalTime(this.merged_at),
        user = User(
            id = this.user?.id,
            login = this.user?.login,
            avatarUrl = this.user?.avatar_url
        )
    )
}