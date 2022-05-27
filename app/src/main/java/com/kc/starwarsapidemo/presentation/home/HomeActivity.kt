package com.kc.starwarsapidemo.presentation.home

import android.os.Bundle
import androidx.lifecycle.Observer
import com.kc.starwarsapidemo.databinding.ActivityHomeBinding
import com.kc.starwarsapidemo.util.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel.openPaneLiveData.observe(this, openPaneObserver)
        homeViewModel.closePaneLiveData.observe(this, closePaneObserver)
        homeViewModel.getPlanetsList()
        openPane()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.openPaneLiveData.removeObserver(openPaneObserver)
        homeViewModel.closePaneLiveData.removeObserver(closePaneObserver)
    }

    override fun onBackPressed() {
        if (binding.slidingPaneLayout.isOpen) {
            super.onBackPressed()
        } else {
            openPane()
        }
    }

    private val openPaneObserver = Observer<Unit> {
        openPane()
    }

    private val closePaneObserver = Observer<Unit> {
        closePane()
    }

    private fun openPane() {
        binding.slidingPaneLayout.openPane()
    }

    private fun closePane() {
        binding.slidingPaneLayout.closePane()
    }


}