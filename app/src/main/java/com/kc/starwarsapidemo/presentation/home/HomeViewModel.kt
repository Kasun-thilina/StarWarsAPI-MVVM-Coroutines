package com.kc.starwarsapidemo.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.starwarsapidemo.data.model.PlanetsResponse
import com.kc.starwarsapidemo.data.repository.PlanetRepository
import com.kc.starwarsapidemo.util.extensions.setError
import com.kc.starwarsapidemo.util.extensions.setLoading
import com.kc.starwarsapidemo.util.extensions.setSuccess
import com.kc.starwarsapidemo.util.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val planetRepository: PlanetRepository) : ViewModel() {
    val planetListLiveData = MutableLiveData<Resource<PlanetsResponse>>()

    fun getPlanetsList() {
        planetListLiveData.setLoading()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val list = planetRepository.getPlanets(1)
                    planetListLiveData.setSuccess(list)
                } catch (throwable: Throwable) {
                    planetListLiveData.setError(throwable.message)
                }
            }
        }
    }
}


