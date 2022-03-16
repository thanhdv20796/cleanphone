package com.example.cleanphone.ui.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.cleanphone.databinding.FragmentBatterySaverBinding
import com.example.cleanphone.databinding.FragmentHomeBinding
import com.example.cleanphone.ui.battery.presentation.BatterySaverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imgBtnSpeedUpPhone.setOnClickListener {
            viewModel.clearAllAppRunBackground()
            binding.txtTotalRam.text = "Tổng Ram: " + viewModel.getAllRam().toString()

        }

        binding.imgBtnBatterySaver.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToBatteryFragment();
            it.findNavController().navigate(action);
        }

        binding.imgBtnCpuCooler.setOnClickListener {

        }

        binding.imgBtnSecurity.setOnClickListener {

        }
        binding.txtTotalRam.text = "Tổng Ram: " + viewModel.getAllRam().toString()
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}