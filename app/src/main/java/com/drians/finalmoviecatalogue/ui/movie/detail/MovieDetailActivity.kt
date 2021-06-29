package com.drians.finalmoviecatalogue.ui.movie.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.drians.finalmoviecatalogue.R
import com.drians.finalmoviecatalogue.data.local.entity.MovieEntity
import com.drians.finalmoviecatalogue.databinding.ActivityMovieDetailBinding
import com.drians.finalmoviecatalogue.databinding.ContentMovieDetailBinding
import com.drians.finalmoviecatalogue.utils.formatDate
import com.drians.finalmoviecatalogue.utils.loadPoster
import com.drians.finalmoviecatalogue.viewmodel.ViewModelFactory
import com.drians.finalmoviecatalogue.utils.gone
import com.drians.finalmoviecatalogue.utils.visible
import com.drians.finalmoviecatalogue.vo.Status

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    private lateinit var contentMovieDetailBinding: ContentMovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        contentMovieDetailBinding = activityMovieDetailBinding.contentMovieDetail

        setContentView(activityMovieDetailBinding.root)

        setSupportActionBar(activityMovieDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        val id = intent.extras?.getInt(EXTRA_MOVIE)
        id?.let { movieId ->
            viewModel.setSelectedMovie(movieId)

            viewModel.movieDetail.observe(this, {
                when (it.status) {
                    Status.LOADING -> activityMovieDetailBinding.progressBar.visible()
                    Status.SUCCESS -> if (it.data != null) {
                        activityMovieDetailBinding.progressBar.gone()
                        populateMovie(it.data)
                    }
                    Status.ERROR -> {
                        activityMovieDetailBinding.progressBar.gone()
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            })
        }
    }

    private fun populateMovie(movie: MovieEntity) {
        contentMovieDetailBinding.apply {
            with(movie) {
                textTitle.text = title
                textReleaseDate.text = releaseDate?.formatDate()
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
        viewModel.movieDetail.observe(this, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> activityMovieDetailBinding.progressBar.visible()
                    Status.SUCCESS -> if (it.data != null) {
                        activityMovieDetailBinding.progressBar.gone()
                        val state = it.data.favorited

                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        activityMovieDetailBinding.progressBar.gone()
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
        const val EXTRA_MOVIE = "extra_movie"
    }
}