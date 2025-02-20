package com.example.githubmagic.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubmagic.PrInfo
import com.example.githubmagic.data.GithubPagingSource
import com.example.githubmagic.data.model.PrInfoDto
import com.example.githubmagic.data.remote.remote.GithubApi
import com.example.githubmagic.data.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubViewRepository @Inject constructor(
    private val githubApi: GithubApi
) {

    suspend fun getPagedPosts(): Flow<PagingData<PrInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi) }
        ).flow
    }
}