package com.duntran.agriapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.duntran.agriapp.R
import com.duntran.agriapp.data.repository.DeviceRepository
import com.duntran.agriapp.databinding.FragmentHomeBinding
import com.duntran.agriapp.ui.base.BaseScreenFragment
import com.duntran.agriapp.ui.main.adapter.HomeAdapter
import com.duntran.agriapp.ui.main.model.EnvironmentData
import com.duntran.agriapp.ui.main.service.DataService
import com.duntran.agriapp.utils.loadImageByResource
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeFragment : BaseScreenFragment<FragmentHomeBinding>() {
    private val deviceRepository = DeviceRepository()

    private val homeAdapter by lazy {
        HomeAdapter().apply {
            listener = object : HomeAdapter.OnClickItemProduct {
                override fun onItemClick(item: HomeItem, position: Int) {
                    navigate(position)

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(requireContext(), DataService::class.java).apply {
            requireContext().startService(this)
        }
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        binding.apply {
            imgTem.loadImageByResource(R.drawable.ic_sun)
            imgWeaCon.loadImageByResource(R.drawable.ic_cloud)
        }
        initRecycleView()

    }

    private fun initRecycleView() {
        binding.rcvHome.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvHome.adapter = homeAdapter
    }

    private fun navigate(position: Int) {
        when(position) {
            0->{
            }
            1->{
            }
            2->{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDeviceSettingFragment())
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EnvironmentData) {
        homeAdapter.updateData(event)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(requireContext(), DataService::class.java).apply {
            requireContext().stopService(this)
        }
    }

}