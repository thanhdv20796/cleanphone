package com.example.cleanphone.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cleanphone.databinding.FragmentHomeBinding
import com.example.cleanphone.databinding.FragmentToolsBinding
import com.example.cleanphone.ui.home.presentation.HomeFragmentDirections

class ToolsFragment: Fragment() {
    private var _binding: FragmentToolsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.imgBtnSpeedUpPhone.setOnClickListener {
        }

        binding.imgBtnBatterySaver.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToBatteryFragment();
            it.findNavController().navigate(action);
        }

        binding.imgBtnCpuCooler.setOnClickListener {

        }

        binding.imgBtnSecurity.setOnClickListener {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}