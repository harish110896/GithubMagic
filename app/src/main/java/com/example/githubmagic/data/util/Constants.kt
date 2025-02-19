package com.example.githubmagic.data.util

sealed class Constants {

    companion object {
        const val BASE_URL_GITHUB = "https://api.github.com"
        const val PAGE_SIZE = 10
        const val MAX_PAGE_COUNT = 20
        const val ACCEPT_TYPE_GSON = "application/vnd.github+json"
        const val DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val DATE_FORMAT_LOCAL = "yyyy-MM-dd HH:mm:ss"

    }
}