package com.drians.finalmoviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drians.finalmoviecatalogue.databinding.FragmentMovieBinding
import com.drians.finalmoviecatalogue.viewmodel.ViewModelFactory
import com.drians.finalmoviecatalogue.utils.gone
import com.drians.finalmoviecatalogue.utils.visible
import com.drians.finalmoviecatalogue.vo.Status

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val adapter = MovieAdapter()
            viewModel.getMovies().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> binding?.progressBar?.visible()
                        Status.SUCCESS -> {
                            binding?.progressBar?.gone()
                            adapter.submitList(movie.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.gone()
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            binding?.rvMovie.apply {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }
}