package com.duntran.agriapp.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duntran.agriapp.databinding.ItemHomeBinding
import com.duntran.agriapp.ui.main.HomeItem
import com.duntran.agriapp.ui.main.model.EnvironmentData
import com.duntran.agriapp.utils.Constants.LIST_ITEM_HOME
import com.duntran.agriapp.utils.setData
import kotlin.random.Random

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var homeItems = LIST_ITEM_HOME
    var listener: OnClickItemProduct? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<HomeItem>) {
        homeItems = data.toMutableList()
        notifyDataSetChanged()
    }

    fun updateData(data: EnvironmentData) {
        homeItems[0].info = "%.1f".format((data.light))
        homeItems[1].info = "%.1f".format((data.humidity))
//        homeItems[2].info = "%.1f °C".format((data.temperature + Random.nextFloat()))
        notifyItemRangeChanged(0, 2)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = homeItems.size

    inner class HomeViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val homeItem = homeItems[position]
            binding.setData(homeItem)
            binding.root.setOnClickListener {
                listener?.onItemClick(homeItem, position)
            }
        }
    }

    interface OnClickItemProduct {
        fun onItemClick(item: HomeItem, position: Int)
    }
}