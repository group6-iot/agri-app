package com.duntran.agriapp.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.duntran.agriapp.MainActivity

abstract class BaseScreenWithViewModelFragment<V : ViewDataBinding> : BaseScreenFragment<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = (requireActivity() as MainActivity)
        initViewModel()
    }

    abstract fun initViewModel()
}