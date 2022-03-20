package com.example.cleanphone.ui.home.presentation

import android.os.Bundle
import android.os.HardwarePropertiesManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.cleanphone.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import kotlin.math.log


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
            val MB = 1024*1024
            val freeMem = viewModel.getAvailableMemory()/MB
            val totalMem = viewModel.getTotalMemory()/MB
            val usedMem = viewModel.getUsageMemory()/MB

            var StringRet = ""
            val numCores = viewModel.getNumberOfCores()
            StringRet += "CPU temperature\n"
            val cpuTemp = viewModel.cpuTemperature()
            StringRet += cpuTemp.toString() + "\n"
            StringRet += "CPU frequences\n"
            for(i in 0..numCores-1){
                val currentFreq = viewModel.getCurrentFreq(i)
                StringRet += "Core ${i}: ${currentFreq}\n"
            }
            val action = HomeFragmentDirections.actionHomeToRamUsageFragment()
            it.findNavController().navigate(action);

        }

        binding.imgBtnBatterySaver.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToBatteryFragments()
            it.findNavController().navigate(action);
        }

        binding.imgBtnCpuCooler.setOnClickListener {
            Log.d("Debug: ", "cpu cooler")
            val numCores = viewModel.getNumberOfCores()
            Log.d("Debug ", "num cores: " + numCores)
            try{
                viewModel.cpuTemperature()
            }
            catch (e: Exception){

            }
            for(i in 0..numCores-1)
                viewModel.getMinMaxFreq(i)

            for(i in 0..numCores-1) {
                val currentFreq = viewModel.getCurrentFreq(i)
                Log.v("Debug ", "Current freq core ${i} -> ${currentFreq} MHz")
            }
        }

        binding.imgBtnSecurity.setOnClickListener {

        }
        val MB = 1024*1024
        val freeMem = viewModel.getAvailableMemory()/MB
        val totalMem = viewModel.getTotalMemory()/MB
        val usedMem = viewModel.getUsageMemory()/MB

        var StringRet = ""
        val numCores = viewModel.getNumberOfCores()
        StringRet += "CPU temperature\n"
        val cpuTemp = viewModel.cpuTemperature()
        StringRet += cpuTemp.toString() + "\n"
        StringRet += "CPU frequences\n"
        for(i in 0..numCores-1){
            val currentFreq = viewModel.getCurrentFreq(i)
            StringRet += "Core ${i}: ${currentFreq}\n"
        }

        Log.d("Debug ", "temp screen: ${StringRet}")

        StringRet += "Total Ram: " + totalMem.toString() + " MB\n"
        StringRet += "Free Ram: " + freeMem.toString() + " MB\n"
        StringRet += "Usage Ram: " + usedMem.toString() + " MB\n"

        binding.txtTotalRam.text = StringRet
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