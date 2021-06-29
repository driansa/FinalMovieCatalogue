package com.drians.finalmoviecatalogue.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.databinding.ItemsTvBinding
import com.drians.finalmoviecatalogue.ui.tv.detail.TvDetailActivity
import com.drians.finalmoviecatalogue.utils.formatDateToYear
import com.drians.finalmoviecatalogue.utils.loadPoster

class TvAdapter : PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {

            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsTvBinding = ItemsTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null)
            holder.bind(tv)
    }

    inner class TvViewHolder(private val binding: ItemsTvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                textName.text = tv.name
                textFirstAirDate.text = tv.firstAirDate?.formatDateToYear()
                textVoteAverage.text = tv.voteAverage.toString()
                tv.posterPath?.let { imagePoster.loadPoster(it) }
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, TvDetailActivity::class.java)
                intent.putExtra(TvDetailActivity.EXTRA_TV_SHOW, tv.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}