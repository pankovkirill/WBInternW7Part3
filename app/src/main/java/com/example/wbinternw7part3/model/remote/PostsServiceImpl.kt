package com.example.wbinternw7part3.model.remote

import com.example.wbinternw7part3.model.remote.dto.ImageResponse
import com.example.wbinternw7part3.model.remote.dto.MessageResponse
import com.example.wbinternw7part3.model.remote.dto.VoteRequest
import com.example.wbinternw7part3.model.remote.dto.VoteResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class PostsServiceImpl(
    private val client: HttpClient
) : PostsService {
    override suspend fun getRandomImage(): List<ImageResponse> {
        return client.get {
            url(HttpRoutes.PICTURE_URL)
        }
    }

    override suspend fun getImageById(id: String): ImageResponse {
        val url = "$BASE_URL$id?$API_KEY"
        return client.get {
            url(url)
        }
    }

    override suspend fun getVoteList(): List<VoteResponse> {
        return client.get {
            url(HttpRoutes.VOTES_URL)
        }
    }

    override suspend fun createVote(postRequest: VoteRequest): MessageResponse {
        return client.post {
            url(HttpRoutes.VOTES_URL)
            contentType(ContentType.Application.Json)
            body = postRequest
        }
    }

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/images/"
        private const val API_KEY = "api_key=b9940f18-3124-4b46-bb7d-2f03a64e11e2"
    }
}