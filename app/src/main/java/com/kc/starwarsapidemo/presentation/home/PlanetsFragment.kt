package com.kc.starwarsapidemo.presentation.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc.starwarsapidemo.data.model.Planet
import com.kc.starwarsapidemo.data.model.PlanetsResponse
import com.kc.starwarsapidemo.databinding.FragmentPlanetsBinding
import com.kc.starwarsapidemo.presentation.home.adapter.PlanetsAdapter
import com.kc.starwarsapidemo.util.base.BaseFragment
import com.kc.starwarsapidemo.util.extensions.dialog.alertDialog
import com.kc.starwarsapidemo.util.extensions.gone
import com.kc.starwarsapidemo.util.extensions.show
import com.kc.starwarsapidemo.util.network.Resource
import com.kc.starwarsapidemo.util.network.ResourceState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlanetsFragment : BaseFragment() {
    private lateinit var binding: FragmentPlanetsBinding
    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var planetsAdapter: PlanetsAdapter
    private lateinit var adapter: PlanetsAdapter
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context).apply {
            isSmoothScrollbarEnabled = true
        }
    }
    private var selectedPlanetPosition = -1

    companion object {
        private const val SELECTED_POSITION = "selected_position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        adapter = PlanetsAdapter(requireContext())
        return binding.root
    }

    private fun init() {
//        homeViewModel.getPlanetsList()
//        planetsAdapter = PlanetsAdapter(requireContext(),mutableListOf()) { planet, index ->
//            selectedPlanetPosition = index
//            planetsAdapter.updateSelectedPosition(index)
//            layoutManager.scrollToPositionWithOffset(index, 0)
//            onPlanetSelected(planet)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.planetListLiveData.observe(viewLifecycleOwner) { observeFetchPlanets(it) }
        init()
        adapter.setTopicClickListener(onTopicClick)
        binding.rvPlanets.layoutManager = layoutManager
        binding.rvPlanets.adapter = adapter

        if (savedInstanceState != null) {
            this.selectedPlanetPosition = savedInstanceState.getInt(SELECTED_POSITION)
//            planetsAdapter.updateSelectedPosition(this.selectedPlanetPosition)
        }
    }

    private val onTopicClick: (Planet, Int) -> Unit = { book, position ->
        this.selectedPlanetPosition = position
        adapter.updateSelectedPosition(position)
//        layoutManager.scrollToPositionWithOffset(position, 0)
        onPlanetSelected(book)
    }

    private fun onPlanetSelected(planet: Planet) {
        Handler().postDelayed({
            homeViewModel.selectPlanet(planet)
            homeViewModel.closePane()
        }, 300)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_POSITION, selectedPlanetPosition)
    }

    private fun observeFetchPlanets(resource: Resource<PlanetsResponse>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                    binding.progress.show()
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()
                    binding.progress.gone()
                    adapter.updatePlanets(it.data?.results!!)
//                    if (!it.data?.results.isNullOrEmpty()) {
//                        planetsAdapter.apply {
//                            planets.clear()
//                            planets.addAll(it.data?.results!!)
//                            notifyDataSetChanged()
//                        }
//                    }
                }
                ResourceState.ERROR -> {
                    hideProgress()
                    binding.progress.gone()
                    alertDialog("Error", it.message) {
                        positiveButton("OK") {

                        }
                    }
                }
            }
        }
    }

}