package com.kc.starwarsapidemo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kc.starwarsapidemo.R
import com.kc.starwarsapidemo.data.model.Planet
import com.kc.starwarsapidemo.databinding.FragmentPlanetDetailsBinding
import com.kc.starwarsapidemo.util.base.BaseFragment
import com.kc.starwarsapidemo.util.extensions.loadImageRound
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PlanetDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentPlanetDetailsBinding
    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.title =
            getString(R.string.planet_details)
        homeViewModel.selectedPlanetLiveData.observe(viewLifecycleOwner, selectedPlanetObserver)
    }

    private val selectedPlanetObserver = Observer<Planet> { planet ->
        binding.apply {
            tvPlanetName.text = planet.name
            tvPlanetOpValue.text = planet.orbitalPeriod
            tvPlanetGravityVal.text = planet.gravity
            ivPlanetImage.loadImageRound("https://picsum.photos/200?temp=${planet.name}", 10)
        }
    }

    private fun openPane() {
        homeViewModel.openPane()
    }
}