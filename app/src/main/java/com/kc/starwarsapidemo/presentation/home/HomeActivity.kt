package com.kc.starwarsapidemo.presentation.home

import android.os.Bundle
import com.kc.starwarsapidemo.data.model.PlanetsResponse
import com.kc.starwarsapidemo.databinding.ActivityHomeBinding
import com.kc.starwarsapidemo.util.base.BaseActivity
import com.kc.starwarsapidemo.util.extensions.dialog.alertDialog
import com.kc.starwarsapidemo.util.network.Resource
import com.kc.starwarsapidemo.util.network.ResourceState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        homeViewModel.planetListLiveData.observe(this) { observeFetchPlanets(it) }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getPlanetsList()
    }

    private fun observeFetchPlanets(resource: Resource<PlanetsResponse>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {

                    hideProgress()
                }
                ResourceState.ERROR -> {
                    hideProgress()
                    alertDialog("Error", it.message) {
                        positiveButton("OK") {

                        }
                    }
                }
            }
        }
    }
}