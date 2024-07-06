package com.lenhujori.stablediffusion

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/sdapi/v1/txt2img")
    @Headers("Content-Type: application/json")
    suspend fun generateImage(@Body requestBody: RequestBody): ResponseBody
}