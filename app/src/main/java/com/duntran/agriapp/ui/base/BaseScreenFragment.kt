package com.duntran.agriapp.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.duntran.agriapp.MainActivity

abstract class BaseScreenFragment<V : ViewDataBinding> : AbsBaseFragment<V>() {
    protected lateinit var mainActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = (requireActivity() as MainActivity)
    }
}