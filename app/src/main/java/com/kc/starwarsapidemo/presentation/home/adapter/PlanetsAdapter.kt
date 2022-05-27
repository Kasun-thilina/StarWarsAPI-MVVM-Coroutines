package com.kc.starwarsapidemo.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kc.starwarsapidemo.R
import com.kc.starwarsapidemo.data.model.Planet

class PlanetsAdapter(var context: Context) : RecyclerView.Adapter<PlanetViewHolder>() {
    private var planet: List<Planet>? = null
    private var selectedIndex = -1
    private var onTopicClick: ((Planet, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder =
        PlanetViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_planet, parent, false)
        )

    override fun getItemCount(): Int = planet?.size ?: 0

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        planet?.get(position)?.let { book ->
            holder.bind(book, selectedIndex == position, context) {
                onTopicClick?.invoke(book, position)
            }
        }
    }

    fun updatePlanets(books: List<Planet>) {
        this.planet = books
        notifyDataSetChanged()
    }

    fun updateSelectedPosition(index: Int) {
        val previousSelectedIndex = this.selectedIndex
        this.selectedIndex = index
        notifyItemChanged(previousSelectedIndex)
        notifyItemChanged(index)
    }

    fun setTopicClickListener(onTopicClick: ((Planet, Int) -> Unit)?) {
        this.onTopicClick = onTopicClick
    }

}