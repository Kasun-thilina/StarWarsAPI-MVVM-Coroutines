package com.kc.starwarsapidemo.data.api

import com.kc.starwarsapidemo.data.model.PlanetsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {
    @GET("planets")
    suspend fun getPlanets(@Query("page") page: Int): PlanetsResponse
}