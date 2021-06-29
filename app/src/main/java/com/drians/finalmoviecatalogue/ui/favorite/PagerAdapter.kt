package com.drians.finalmoviecatalogue.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.drians.finalmoviecatalogue.R
import com.drians.finalmoviecatalogue.ui.favorite.movie.FavoriteMovieFragment
import com.drians.finalmoviecatalogue.ui.favorite.tv.FavoriteTvFragment


class PagerAdapter(private val mContext: Context, fa: FragmentActivity) : FragmentStateAdapter(fa) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_movie, R.string.title_tv)
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvFragment()
            else -> Fragment()
        }

    fun getPageTitle(position: Int): CharSequence = mContext.resources.getString(TAB_TITLES[position])

    override fun getItemCount(): Int = 2
}