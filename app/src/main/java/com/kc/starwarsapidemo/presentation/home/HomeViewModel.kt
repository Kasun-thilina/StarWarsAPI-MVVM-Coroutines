package com.kc.starwarsapidemo.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.starwarsapidemo.data.model.Planet
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
    val selectedPlanetLiveData = MutableLiveData<Planet>()
    val openPaneLiveData = MutableLiveData<Unit>()
    val closePaneLiveData = MutableLiveData<Unit>()

    var currentPage: Int = 1
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    fun getPlanetsList() {
        planetListLiveData.setLoading()
        isLoading = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    isLoading = false
                    val list = planetRepository.getPlanets(currentPage)
                    if (list.next == null) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                    planetListLiveData.setSuccess(list)
                } catch (throwable: Throwable) {
                    isLoading = false
                    planetListLiveData.setError(throwable.message)
                }
            }
        }
    }

    fun selectPlanet(planet: Planet) {
        selectedPlanetLiveData.value = planet
    }

    fun openPane() {
        openPaneLiveData.value = Unit
    }

    fun closePane() {
        closePaneLiveData.value = Unit
    }

}


