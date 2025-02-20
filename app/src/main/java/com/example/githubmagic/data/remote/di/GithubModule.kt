package com.example.githubmagic.data.remote.di

import com.example.githubmagic.data.repository.GithubRepository
import com.example.githubmagic.data.repository.impl.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GithubModule {
    @Binds
    abstract fun bindRepository(
        repositoryImpl: GithubRepositoryImpl
    ): GithubRepository
}