import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class DeviceInfo {
  final String imsi;
  final String imei;
  final String operator;
  final String version;
  final String brand;
  final String model;
  final String platform;

  const DeviceInfo(
      {this.imsi, this.imei, this.operator, this.version, this.brand, this.model, this.platform});

  static DeviceInfo android(Map<dynamic, dynamic> json) {
    return DeviceInfo(
        imei: json['imei'],
        imsi: json['imsi'],
        operator: json['operator'],
        brand: json['brand'],
        model: json['model'],
        version: json['version'],
        platform: json['platform']);
  }

  static DeviceInfo iOS(Map<dynamic, dynamic> json) {
    return DeviceInfo(
        imei: json['imei'],
        imsi: json['imsi'],
        operator: json['operator'],
        brand: json['brand'],
        model: json['model'],
        version: json['version'],
        platform: json['platform']);
  }
}

class FlutterDevicePlugin {
  static const MethodChannel _channel = const MethodChannel('com.chuangdun.flutter/DeviceInfo');

  static Future<DeviceInfo> get deviceInfo async {
    final Map<dynamic, dynamic> result = await _channel.invokeMethod('getDeviceInfo');
    if (defaultTargetPlatform == TargetPlatform.android) {
      return DeviceInfo.android(result);
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      return DeviceInfo.iOS(result);
    }
    return null;
  }
}
