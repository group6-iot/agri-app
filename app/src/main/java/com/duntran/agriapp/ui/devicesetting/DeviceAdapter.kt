package com.duntran.agriapp.ui.devicesetting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duntran.agriapp.data.model.DeviceResponse
import com.duntran.agriapp.databinding.ItemDeviceBinding
import com.duntran.agriapp.utils.setData
import java.util.*

class DeviceAdapter: RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private var deviceItems = mutableListOf<DeviceResponse>()
    var listener: OnClickItemDevice? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DeviceResponse>) {
        deviceItems = data.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = deviceItems.size

    inner class DeviceViewHolder(private val binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val homeItem = deviceItems[position]
            binding.setData(homeItem, binding.root.context, position)
            binding.btnEdit.setOnClickListener {
                listener?.onEditClick(homeItem, position)
            }
        }
    }

    interface OnClickItemDevice {
        fun onEditClick(item: DeviceResponse, position: Int)
    }
}