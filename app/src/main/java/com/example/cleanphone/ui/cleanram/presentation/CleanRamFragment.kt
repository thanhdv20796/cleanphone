package com.example.cleanphone.ui.cleanram.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cleanphone.data.Resource
import com.example.cleanphone.data.Status
import com.example.cleanphone.data.model.local.BatteryStatus
import com.example.cleanphone.databinding.FragmentUsageRamBinding
import com.example.cleanphone.ui.battery.presentation.BatterySaverAdapter
import com.example.cleanphone.ui.battery.presentation.BatterySaverViewModel
import com.example.cleanphone.ui.cleanram.data.RamUsageInfo
import com.example.cleanphone.utils.RamUtils
import com.example.cleanphone.utils.showSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_battery_saver.*

@AndroidEntryPoint
class CleanRamFragment : Fragment() {
    private var _binding: FragmentUsageRamBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CleanRamViewModel by viewModels()

    // This property is only valid between onCreateView and
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsageRamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textBindingRam();
        context?.let { viewModel.getAllListApplicationRun(it) }

        viewModel.ramUsageInfoData.observe(viewLifecycleOwner, ::handleListInfoRam)
        binding.cleanRam.setOnClickListener {
            context?.let { it1 -> viewModel.clearRam(it1) }
            binding.rvUsageRam.visibility = View.GONE
            textBindingRam()
        }
    }

    private fun handleListInfoRam(resource: Resource<List<RamUsageInfo>?>) {
        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { item ->
                val adapter = CleanRamAdapter(item)
                binding.rvUsageRam.run {
                    this.setHasFixedSize(true)
                    this.adapter = adapter
                }
            }
            Status.ERROR -> {
                resource.errorMessage?.let { binding.root.showSnackbar(it, Snackbar.LENGTH_SHORT) }
            }
            else -> {
            }
        }

    }


    fun textBindingRam() {
        binding.totalRam.text = context?.let { RamUtils(it).readInfoRam() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}