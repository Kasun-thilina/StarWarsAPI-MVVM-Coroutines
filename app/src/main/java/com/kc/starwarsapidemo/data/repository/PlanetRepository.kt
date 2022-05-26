package com.kc.starwarsapidemo.data.repository

import com.kc.starwarsapidemo.data.api.StarWarsApi

class PlanetRepository(private val starWarsApi: StarWarsApi) {
    suspend fun getPlanets(page: Int) = starWarsApi.getPlanets(page)
}