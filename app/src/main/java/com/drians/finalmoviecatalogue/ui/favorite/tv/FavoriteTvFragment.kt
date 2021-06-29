package com.drians.finalmoviecatalogue.ui.favorite.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drians.finalmoviecatalogue.R
import com.drians.finalmoviecatalogue.databinding.FragmentFavoriteTvBinding
import com.drians.finalmoviecatalogue.utils.gone
import com.drians.finalmoviecatalogue.utils.visible
import com.drians.finalmoviecatalogue.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteTvFragment : Fragment() {

    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FavoriteTvViewModel
    private lateinit var adapter: FavoriteTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTvBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteTv)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTvViewModel::class.java]

            adapter = FavoriteTvAdapter()
            binding?.progressBar?.visible()
            viewModel.getFavorites().observe(viewLifecycleOwner, {
                binding?.progressBar?.gone()
                adapter.submitList(it)
            })

            binding?.rvFavoriteTv.apply {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val tvEntity = adapter.getSwipedData(swipedPosition)
                tvEntity?.let { viewModel.setFavorite(it) }

                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    tvEntity?.let { viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }
    })
}