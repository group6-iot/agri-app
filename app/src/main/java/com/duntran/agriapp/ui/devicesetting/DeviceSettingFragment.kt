package com.duntran.agriapp.ui.devicesetting

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.duntran.agriapp.R
import com.duntran.agriapp.data.api.response.DataResponse
import com.duntran.agriapp.data.api.response.LoadingStatus
import com.duntran.agriapp.data.model.DeviceResponse
import com.duntran.agriapp.databinding.FragmentDeviceSettingBinding
import com.duntran.agriapp.ui.base.BaseScreenWithViewModelFragment
import com.duntran.agriapp.ui.main.adapter.HomeAdapter


class DeviceSettingFragment : BaseScreenWithViewModelFragment<FragmentDeviceSettingBinding>() {
    private lateinit var mViewModel: DeviceSettingViewModel
    private val adapter by lazy {
        DeviceAdapter().apply {
            listener = object : DeviceAdapter.OnClickItemDevice {
                override fun onEditClick(item: DeviceResponse, position: Int) {
                    navigateToDetail(item)
                }
            }
        }
    }
    override fun getLayout(): Int {
        return R.layout.fragment_device_setting
    }

    override fun initView() {
        binding.viewModel = mViewModel
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        initRecycleView()
    }

    override fun initViewModel() {
        val factory = DeviceSettingViewModel.Factory(requireActivity().application)
        mViewModel = ViewModelProvider(this, factory)[DeviceSettingViewModel::class.java]

        mViewModel.device.observe(this) {
            it?.let {
                if (it.loadingStatus == LoadingStatus.Success) {
                    val body = (it as DataResponse.DataSuccess).body
                    adapter.setData(body)
                }
            }
        }
    }

    private fun initRecycleView() {
        binding.rcvDevice.adapter = adapter
    }

    private fun navigateToDetail(device: DeviceResponse) {
        findNavController().navigate(DeviceSettingFragmentDirections.actionDeviceSettingFragmentToSetupDeviceFragment(device))
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getAll()
    }
}