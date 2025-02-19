package com.example.githubmagic.data.remote.remote

import com.example.githubmagic.data.model.PrInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/repos/square/retrofit/pulls")
    suspend fun getPrList(
        @Query("state") state: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemCount: Int
    ): List<PrInfoDto>
}