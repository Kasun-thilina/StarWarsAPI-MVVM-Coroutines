package com.kc.starwarsapidemo.di

import com.kc.starwarsapidemo.data.repository.PlanetRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PlanetRepository(get()) }
}