# flutter_device_plugin

Flutter 读取设备信息插件

## Getting Started

1.在pubspec.yaml中添加插件依赖项

```yaml
  dependencies:
    flutter_device_plugin:
      git: git://github.com/scofield-hello/flutter_device_plugin.git
```

2.使用FlutterDevicePlugin

```dart
import 'package:flutter/services.dart';
import 'package:flutter_device_plugin/flutter_device_plugin.dart';

Future<void> readDeviceInfo() async {
    try {
      DeviceInfo deviceInfo = await FlutterDevicePlugin.deviceInfo;
      print(deviceInfo.imsi);//IMSI,多个用*连接
      print(deviceInfo.imei);//IMEI,多个用*连接
      print(deviceInfo.operator);//网络运营商
      print(deviceInfo.brand);//手机品牌
      print(deviceInfo.model);//手机型号
      print(deviceInfo.platform);//android /iOS
      print(deviceInfo.version);//系统版本，platform为android时，值为Build.VERSION.SDK_INT
    } on PlatformException catch (e) {
      print(e);
    }
  }
```



This project is a starting point for a Flutter
[plug-in package](https://flutter.dev/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our 
[online documentation](https://flutter.dev/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
