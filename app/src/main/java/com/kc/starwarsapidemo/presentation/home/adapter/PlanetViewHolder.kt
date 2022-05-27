package com.kc.starwarsapidemo.presentation.home.adapter

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kc.starwarsapidemo.R
import com.kc.starwarsapidemo.data.model.Planet
import com.kc.starwarsapidemo.util.extensions.loadImageRound
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_planet.*

class PlanetViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun bind(planet: Planet, isSelected: Boolean, context: Context, onClick: () -> Unit) {
        tvPlanetName.text = planet.name
        tvPlanetClimate.text = planet.climate
        clParent.setOnClickListener { onClick() }
        ivPlanetImage.loadImageRound("https://picsum.photos/200?temp=${planet.name}", 10)
        if (isSelected) {
            clParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        } else {
            clParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }
    }
}
