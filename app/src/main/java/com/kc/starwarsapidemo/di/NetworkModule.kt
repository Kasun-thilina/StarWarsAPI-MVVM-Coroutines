package com.kc.starwarsapidemo.di

import com.kc.starwarsapidemo.BuildConfig
import com.kc.starwarsapidemo.data.api.StarWarsApi
import com.kc.starwarsapidemo.util.network.SupportInterceptor
import com.kc.starwarsapidemo.util.network.createNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

private val supportInterceptor = SupportInterceptor()
val networkModule: Module = module {
    single {
        createNetworkClient(
            androidContext(),
            BuildConfig.STAR_WARS_URL,
            BuildConfig.DEBUG,
            supportInterceptor
        )
    }
    single { get<Retrofit>().create(StarWarsApi::class.java) }
    single { supportInterceptor }
}