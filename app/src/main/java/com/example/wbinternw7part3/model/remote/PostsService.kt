package com.example.wbinternw7part3.model.remote

import com.example.wbinternw7part3.model.remote.dto.ImageResponse
import com.example.wbinternw7part3.model.remote.dto.MessageResponse
import com.example.wbinternw7part3.model.remote.dto.VoteRequest
import com.example.wbinternw7part3.model.remote.dto.VoteResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json

interface PostsService {
    suspend fun getRandomImage(): List<ImageResponse>

    suspend fun getImageById(id: String): ImageResponse

    suspend fun createVote(postRequest: VoteRequest): MessageResponse

    suspend fun getVoteList(): List<VoteResponse>

    companion object {
        fun create(): PostsService {
            return PostsServiceImpl(
                HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                }
            )
        }
    }
}