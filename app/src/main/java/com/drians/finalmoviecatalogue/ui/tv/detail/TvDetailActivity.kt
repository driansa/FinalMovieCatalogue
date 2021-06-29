package com.drians.finalmoviecatalogue.ui.tv.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.drians.finalmoviecatalogue.R
import com.drians.finalmoviecatalogue.data.local.entity.TvEntity
import com.drians.finalmoviecatalogue.databinding.ActivityTvDetailBinding
import com.drians.finalmoviecatalogue.databinding.ContentTvDetailBinding
import com.drians.finalmoviecatalogue.utils.formatDate
import com.drians.finalmoviecatalogue.utils.loadPoster
import com.drians.finalmoviecatalogue.viewmodel.ViewModelFactory
import com.drians.finalmoviecatalogue.utils.gone
import com.drians.finalmoviecatalogue.utils.visible
import com.drians.finalmoviecatalogue.vo.Status

class TvDetailActivity : AppCompatActivity() {

    private lateinit var activityTvDetailBinding: ActivityTvDetailBinding
    private lateinit var contentTvDetailBinding: ContentTvDetailBinding

    private lateinit var viewModel: TvDetailViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityTvDetailBinding = ActivityTvDetailBinding.inflate(layoutInflater)
        contentTvDetailBinding = activityTvDetailBinding.contentTvDetail

        setContentView(activityTvDetailBinding.root)

        setSupportActionBar(activityTvDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TvDetailViewModel::class.java]

        val id = intent.extras?.getInt(EXTRA_TV_SHOW)
        id?.let { tvId ->
            viewModel.setSelectedTv(tvId)

            viewModel.tvDetail.observe(this, {
                when (it.status) {
                    Status.LOADING -> activityTvDetailBinding.progressBar.visible()
                    Status.SUCCESS -> if (it.data != null) {
                        activityTvDetailBinding.progressBar.gone()
                        populateTv(it.data)
                    }
                    Status.ERROR -> {
                        activityTvDetailBinding.progressBar.gone()
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            })
        }
    }

    private fun populateTv(tv: TvEntity) {
        contentTvDetailBinding.apply {
            with(tv) {
                textName.text = name
                textFirstAirDate.text = firstAirDate?.formatDate()
                textOverview.text = overview
                textVoteAverage.text = voteAverage.toString()
                posterPath?.let { imagePoster.loadPoster(it) }
                posterPath?.let { imageHeader.loadPoster(it) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        viewModel.tvDetail.observe(this, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> activityTvDetailBinding.progressBar.visible()
                    Status.SUCCESS -> if (it.data != null) {
                        activityTvDetailBinding.progressBar.gone()
                        val state = it.data.favorited

                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        activityTvDetailBinding.progressBar.gone()
                        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited_black)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black)
        }
    }

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }
}