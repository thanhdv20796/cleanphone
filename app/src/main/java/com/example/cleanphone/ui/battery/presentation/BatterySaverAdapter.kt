package com.example.cleanphone.ui.battery.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanphone.R
import com.example.cleanphone.domain.model.BatteryStatus
import kotlinx.android.synthetic.main.item_battery_consumption_fragment.view.*

class BatterySaverAdapter(private val batteryStatusItems: List<BatteryStatus>) :
    RecyclerView.Adapter<BatterySaverAdapter.BatteryStatusListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatteryStatusListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_battery_consumption_fragment, parent, false)
        return BatteryStatusListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BatteryStatusListViewHolder, position: Int) {
        holder.bindView(batteryStatusItems.get(position))
    }

    override fun getItemCount(): Int {
        return batteryStatusItems.size
    }


    class BatteryStatusListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvCapacityRemainingPercentage: TextView = itemView.tvCapacityRemainingPercentage
        private val tvDate: TextView = itemView.tvDate
        private val tvHealth: TextView = itemView.tvHealth
        private val tvTemperature: TextView = itemView.tvTemperature
        private val tvPowerSource: TextView = itemView.tvPowerSource
        private val tvStatus: TextView = itemView.tvStatus

        fun bindView(itemBatteryStatus: BatteryStatus) {
            with(itemBatteryStatus) {
                tvCapacityRemainingPercentage.text = "${this.capacityRemainingPercentage}"
                tvDate.text = this.date.toString()
                tvHealth.text = this.health.toString()
                tvTemperature.text = this.temperature.toString()
                tvPowerSource.text = this.powerSource.toString()
                tvStatus.text = this.status.toString()
            }

        }
    }
}