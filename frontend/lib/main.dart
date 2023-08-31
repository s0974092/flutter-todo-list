import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:todo_app/todo_response.dart';
import 'package:flutter/foundation.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Todo App'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool todoDone = true;
  TodoResponse? todoResponse;

  void _fetchApiData() async {
    /*
      因應android和iOS模擬器讀取localhost不同而給定不同的host設定
      https://medium.com/@podcoder/connecting-flutter-application-to-localhost-a1022df63130
    */
    final uriForLocalAndroidEmulator =
        Uri(scheme: 'http', host: '10.0.2.2', path: 'api/tasks', port: 8080);
    final uriForLocalIOSEmulator =
        Uri(scheme: 'http', host: 'localhost', path: 'api/tasks', port: 8080);

    var response;

    if (defaultTargetPlatform == TargetPlatform.android) {
      response = await http.get(uriForLocalAndroidEmulator);
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      response = await http.get(uriForLocalIOSEmulator);
    }

    if (response.statusCode == 200) {
      setState(() =>
          todoResponse = TodoResponse.fromJson(jsonDecode(response.body)));
    }
  }

  @override
  void didChangeDependencies() {
    _fetchApiData();
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: todoResponse != null
          ? ListView.builder(
              itemCount: todoResponse?.data?.length,
              itemBuilder: (context, index) {
                var objData = todoResponse!.data![index];
                return ListTile(
                  leading: Checkbox(
                    onChanged: (bool? value) =>
                        setState(() => objData.completed = value),
                    value: objData.completed,
                  ),
                  title: Text(objData.title!),
                  subtitle: Text(objData.createdAt!),
                );
              })
          : const Center(child: CircularProgressIndicator()),
    );
  }
}
