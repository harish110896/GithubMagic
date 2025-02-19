package com.example.githubmagic.data.model

data class PrInfoDto(
    val id: Long?,
    val title: String?,
    val created_at: String?,
    val closed_at: String?,
    val merged_at: String?,
    val user: UserDto?
)

data class UserDto(
    val id: Long?,
    val login: String?,
    val avatar_url: String?
)