package com.chuangdun.flutter.plugin.device

import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterDevicePlugin {
    companion object {

        private const val CHANNEL_NAME = "com.chuangdun.flutter/DeviceInfo"

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), CHANNEL_NAME)
            channel.setMethodCallHandler(DeviceMethodHandler(registrar.activity()))
        }
    }
}
