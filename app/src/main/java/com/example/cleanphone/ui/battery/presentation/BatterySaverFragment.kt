package com.example.cleanphone.ui.battery.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cleanphone.data.Resource
import com.example.cleanphone.data.Status
import com.example.cleanphone.databinding.FragmentBatterySaverBinding
import com.example.cleanphone.data.model.local.BatteryStatus
import com.google.android.material.snackbar.Snackbar
import com.ymo.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_battery_saver.*

@AndroidEntryPoint
class BatterySaverFragment : Fragment() {
    private var _binding: FragmentBatterySaverBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BatterySaverViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatterySaverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadAllHistoryCharging()
        viewModel.listHistoryBatteryCharging.observe(viewLifecycleOwner, ::moviesHandler)
    }


    private fun moviesHandler(resource: Resource<List<BatteryStatus>?>) {
        when (resource.status) {
            Status.SUCCESS -> resource.data?.let {
                it.let {
                    val adapter = BatterySaverAdapter(it)
                    rvItems.run {
                        this.setHasFixedSize(true)
                        this.adapter = adapter
                    }
                }

            }
            Status.ERROR -> {
                resource.errorMessage?.let { binding.root.showSnackbar(it, Snackbar.LENGTH_SHORT) }
            }
            else -> {
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}