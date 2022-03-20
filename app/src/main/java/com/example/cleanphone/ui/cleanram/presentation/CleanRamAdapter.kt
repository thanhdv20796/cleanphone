package com.example.cleanphone.ui.cleanram.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanphone.R
import com.example.cleanphone.ui.cleanram.data.RamUsageInfo
import kotlinx.android.synthetic.main.item_ram_usage_fragment.view.*

class CleanRamAdapter(private val listRamUsageInfo: List<RamUsageInfo>) :
    RecyclerView.Adapter<CleanRamAdapter.ListApplicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListApplicationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ram_usage_fragment, parent, false)
        return ListApplicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListApplicationViewHolder, position: Int) {
        holder.bindView(listRamUsageInfo[position])
    }

    override fun getItemCount(): Int {
        return listRamUsageInfo.size
    }


    class ListApplicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val avtLogoApp: ImageView = itemView.icon_app
        private val tvNameApp: TextView = itemView.name_app
        private val tvStatus: CheckBox = itemView.checkbox

        fun bindView(ramUsageInfo: RamUsageInfo) {
            with(ramUsageInfo) {
                avtLogoApp.setImageDrawable(ramUsageInfo.iconApp)
                tvNameApp.text = ramUsageInfo.appName
                tvStatus.isChecked = ramUsageInfo.isCheckBox
            }

        }
    }
}