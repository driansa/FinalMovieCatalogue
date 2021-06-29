package com.drians.finalmoviecatalogue.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drians.finalmoviecatalogue.databinding.FragmentTvBinding
import com.drians.finalmoviecatalogue.viewmodel.ViewModelFactory
import com.drians.finalmoviecatalogue.utils.gone
import com.drians.finalmoviecatalogue.utils.visible
import com.drians.finalmoviecatalogue.vo.Status

class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]

            val adapter = TvAdapter()
            viewModel.getTvs().observe(viewLifecycleOwner, { tv ->
                if (tv != null) {
                    when (tv.status) {
                        Status.LOADING -> binding?.progressBar?.visible()
                        Status.SUCCESS -> {
                            binding?.progressBar?.gone()
                            adapter.submitList(tv.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.gone()
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            binding?.rvTv.apply {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }
}