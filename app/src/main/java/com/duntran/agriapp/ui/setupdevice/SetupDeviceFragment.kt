package com.duntran.agriapp.ui.setupdevice

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.duntran.agriapp.R
import com.duntran.agriapp.data.model.DeviceResponse
import com.duntran.agriapp.databinding.FragmentSetupDeviceBinding
import com.duntran.agriapp.ui.base.BaseScreenWithViewModelFragment
import com.duntran.agriapp.utils.DateUtils
import com.duntran.agriapp.utils.StringUtils
import com.duntran.agriapp.utils.ToastUtils
import com.duntran.agriapp.utils.loadImageByResource


class SetupDeviceFragment : BaseScreenWithViewModelFragment<FragmentSetupDeviceBinding>() {

    private var mViewModel: SetupDeviceViewModel? = null
    private val args by navArgs<SetupDeviceFragmentArgs>()

    override fun getLayout(): Int {
        return R.layout.fragment_setup_device
    }


    override fun initView() {

        binding.apply {
            viewModel = mViewModel
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            timePicker.setIs24HourView(true)

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mViewModel?.changeDeviceMode(
                        when(checkedId) {
                            R.id.rbAuto -> "auto"
                            R.id.rbManual -> "manual"
                            else -> "schedule"
                        }
                    )
                }
            }
            btnSchedule.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mViewModel?.schedule(timePicker.hour,
                        timePicker.minute, edtDuration.text.toString().toInt())
                }
            }
        }
        mViewModel!!.device.observe(this) {
            it?.let {
                setData(it)
            }
        }

        mViewModel!!.scheduleStatus.observe(this) {
            it?.let {
                if (it) {
                    ToastUtils.getInstance(requireContext()).showToast("Schedule success!")
                }
            }
        }

    }

    override fun initViewModel() {
        val deviceResponse = args.device
        val factory = SetupDeviceViewModel.Factory(mainActivity.application)
        mViewModel = ViewModelProvider(this, factory)[SetupDeviceViewModel::class.java]
        mViewModel!!.setDevice(deviceResponse)
    }

    private fun setData(device: DeviceResponse) {
        binding.apply {
            toolbar.title = device.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tvUpdatedAt.text = String.format(DateUtils.getSimpleDate(device.updated_at))
                tvCreatedAt.text = String.format(DateUtils.getSimpleDate(device.created_at))
            } else {
                tvUpdatedAt.text = device.updated_at
                tvCreatedAt.text = device.created_at
            }
            tvStatus.text = StringUtils.standardized(device.status)
            tvStatus.setTextColor(if (device.status == "on") {
                ContextCompat.getColor(requireContext(), R.color.green)
            } else {
                ContextCompat.getColor(requireContext(), R.color.red)
            })
            Log.e("----", "setData: ${device.status}", )
            radioGroup.check(
                when (device.mode) {
                    "auto" -> {
                        R.id.rbAuto
                    }
                    "manual" -> {
                        R.id.rbManual
                    }
                    else -> {
                        R.id.rbSchedule
                    }
                }
            )
            switchOnOff.isChecked = device.status == "on"
            if (device.name == "Pump")
                ivDevice.loadImageByResource(R.drawable.ic_pump)
            else ivDevice.loadImageByResource(R.drawable.ic_lamp)
        }

    }
}