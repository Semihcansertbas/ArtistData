package com.gminterview.artistdata

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gminterview.artistdata.databinding.ListItemTrackBinding
import com.gminterview.artistdata.model.Tracks

class TracksAdapter: RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    inner class TracksViewHolder(val binding: ListItemTrackBinding):
        RecyclerView.ViewHolder(binding.root)

    //diffUtil
    private val diffCallback = object : DiffUtil.ItemCallback<Tracks>() {
        override fun areItemsTheSame(oldItem: Tracks, newItem: Tracks): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: Tracks, newItem: Tracks): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var tracks: List<Tracks>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        return TracksViewHolder(
            ListItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = tracks[position]

        //set data into list item
        holder.binding.apply {
            tvArtistName.text = track.artistName
            tvTrackName.text = track.trackName
            tvTrackPrice.text = "Price: ${track.trackPrice}$"
            tvTrackReleaseDate.text = "Released On: ${track.releaseDate}"
            tvGenre.text = "Genre: ${track.primaryGenreName}"
        }
    }

    override fun getItemCount(): Int = tracks.size
}