package com.duntran.agriapp.ui.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any>(
    private val layout: Int
) : RecyclerView.Adapter<BaseViewHolder>(){

    private lateinit var inflater: LayoutInflater

    var list = mutableListOf<T>()
    var listener: BaseListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            layout,
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding.apply {
//            setVariable(BR.item, list?.get(position))
//            setVariable(BR.itemPosition, position)
//            setVariable(BR.itemListener, listener)
            val context = root.context as LifecycleOwner
            lifecycleOwner = context
            executePendingBindings()
        }
    }


    override fun getItemCount(): Int = list.size ?: 0

    fun submit(newData : List<T>?){
        val new = newData?.toMutableList()
        this.list = new!!
        notifyDataSetChanged()

    }

}
