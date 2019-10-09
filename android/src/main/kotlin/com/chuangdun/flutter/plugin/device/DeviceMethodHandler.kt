package com.chuangdun.flutter.plugin.device

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import cn.richinfo.dualsim.TelephonyManagement
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.lang.ref.WeakReference

private const val METHOD_DEVICE_INFO = "getDeviceInfo"
private const val REQUEST_PERMISSIONS = 1

open class DeviceMethodHandler(activity: Activity) : MethodChannel.MethodCallHandler {

    private val activityRef = WeakReference<Activity>(activity)

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (!checkPermissions()) {
            result.error("PERMISSION_DENIED", "请在授予应用必要的权限后重试.", null)
            requestPermissions()
        }
        when (call.method) {
            METHOD_DEVICE_INFO -> {
                result.success(getDeviceInfo())
            }
            else -> result.notImplemented()
        }
    }

    protected open fun checkPermissions(): Boolean {
        val activity = activityRef.get()!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }


    protected open fun requestPermissions() {
        val activity = activityRef.get()!!
        ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_PERMISSIONS)
    }

    protected open fun getDeviceInfo(): HashMap<String, Any> {
        val activity = activityRef.get()!!
        val telephonyInfo = TelephonyManagement
                .getInstance().getTelephonyInfo(activity)
        val imei1 = telephonyInfo.imeiSIM1
        val imsi1 = telephonyInfo.imsiSIM1
        val imei2 = telephonyInfo.imeiSIM2
        val imsi2 = telephonyInfo.imsiSIM2
        val operatorSIM1 = telephonyInfo.operatorSIM1
        val operatorSIM2 = telephonyInfo.operatorSIM2
        val imeiList = mutableSetOf<String>(imei1, imei2).filterNot { it.isNullOrBlank() }
        val imsiList = mutableSetOf<String>(imsi1, imsi2).filterNot { it.isNullOrBlank() }
        val opratorList = mutableSetOf<String>(operatorSIM1, operatorSIM2).filterNot { it.isNullOrBlank() }
        val imeiString = imeiList.joinToString(separator = "*")
        val imsiString = imsiList.joinToString(separator = "*")
        val operatorString = opratorList.joinToString(separator = "*")
        return hashMapOf("imsi" to imsiString, "imei" to imeiString, "operator" to operatorString,
                "version" to "${Build.VERSION.SDK_INT}", "brand" to Build.BRAND,
                "model" to Build.MODEL, "platform" to "android")
    }
}