import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_device_plugin/flutter_device_plugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  DeviceInfo _deviceInfo;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    DeviceInfo deviceInfo;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      deviceInfo = await FlutterDevicePlugin.deviceInfo;

      print(deviceInfo.imsi);
      print(deviceInfo.imei);
      print(deviceInfo.operator);
      print(deviceInfo.brand);
      print(deviceInfo.model);
      print(deviceInfo.platform);
      print(deviceInfo.version);
    } on PlatformException catch (e) {
      print(e);
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _deviceInfo = deviceInfo;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: _deviceInfo == null
              ? Text("DEVICE INFO READ FAILED.")
              : Text('DEVICE INFO: ${_deviceInfo.imsi}\n'),
        ),
      ),
    );
  }
}
