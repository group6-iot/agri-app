package com.duntran.agriapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.duntran.agriapp.databinding.ActivityMainBinding
import com.duntran.agriapp.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getFragmentID(): Int {
        return R.id.navContainerViewMain
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    fun navigationUp(args: NavDirections){
        navHostFragment.findNavController().navigate(args)
    }

}