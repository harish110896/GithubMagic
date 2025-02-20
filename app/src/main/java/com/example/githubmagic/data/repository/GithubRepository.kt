package com.example.githubmagic.data.repository

import androidx.paging.PagingData
import com.example.githubmagic.data.model.PrInfoDto
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getPagedPosts(): Flow<PagingData<PrInfoDto>>
}