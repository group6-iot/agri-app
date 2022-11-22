package com.duntran.agriapp.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.duntran.agriapp.R
import com.duntran.agriapp.data.model.DeviceResponse
import com.duntran.agriapp.databinding.ItemDeviceBinding
import com.duntran.agriapp.databinding.ItemHomeBinding
import com.duntran.agriapp.ui.main.HomeItem
import java.time.LocalDateTime

@BindingAdapter("android:bindThumbnailFile")
fun ImageView.loadImageByUrl(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.default_image)
        .error(R.drawable.default_image).into(this)
}
@BindingAdapter("android:loadImageByResource")
fun ImageView.loadImageByResource(image: Int) {
    Glide.with(this)
        .load(image)
        .placeholder(R.drawable.default_image)
        .error(R.drawable.default_image).into(this)
}

fun ItemHomeBinding.setData(homeItem: HomeItem) {
    tvInfo.text = homeItem.info
    tvTitle.text = homeItem.title
    imgIcon.loadImageByResource(homeItem.image)
}


fun ItemDeviceBinding.setData(device: DeviceResponse, context: Context, position: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        tvLastUpdate.text = String.format("Last update: ${DateUtils.getSimpleDate(device.updated_at)}")
    } else {
        tvLastUpdate.text = device.updated_at
    }
    tvDeviceName.text = device.name
    tvStatus.text = StringUtils.standardized(device.status)
    tvStatus.setTextColor(if (device.status == "on") {
        ContextCompat.getColor(context, R.color.green)
    } else {
        ContextCompat.getColor(context, R.color.red)
    })
    tvDeviceMode.text = StringUtils.standardized(device.mode)
    tvDeviceMode.setTextColor(if (device.mode == "manual") {
        ContextCompat.getColor(context, R.color.manual_color)
    } else if (device.mode == "auto"){
        ContextCompat.getColor(context, R.color.auto_color)
    }
    else {
        ContextCompat.getColor(context, R.color.schedule_color)
    })
    ivDevice.loadImageByResource(
        if (position == 0){
            R.drawable.ic_pump
        }
        else {
            R.drawable.ic_lamp
        }
    )
}
