package com.nikbrik.openweathermapclient.ui.start

import androidx.recyclerview.widget.RecyclerView
import com.nikbrik.openweathermapclient.databinding.StartListItemAddBinding
import com.nikbrik.openweathermapclient.databinding.StartListItemBinding

class OneCallDataHolder(
    private val binding: StartListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(text: String) {
        binding.textView.text = text
    }
}

class PlaceholderViewHolder(
    private val binding: StartListItemAddBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
    }
}
