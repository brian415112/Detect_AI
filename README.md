# Home Self-Health Monitoring system

## Introduction
We design a system (mobile phone application) that makes users easily operate. Users just need a mobile phone and a sensor, then can do self-health monitoring. It also helps us reach telemedicine（telehealth）in crises, such as epidemics or other situations.

Our system mainly collects three kinds of values, namely electrocardiogram, blood oxygen, and pulse by the sensor. Users can see the real-time data at the same time when they are detecting, and see the result of measurement at the end of the detection. After users finish detecting, they can choose to draw a picture as a measurement diary or not. 

Furthermore, when the user browses the history of detecting, if the user had drawn a picture of measurement diary, the user also can see this picture to know the feelings in the detecting recently. We hope and expect this design can encourage users to like monitoring self-health.

Moreover, users are allowed to use measurement results with disease descriptions for uploading a case. Then doctors can pick some cases they would like to respond to. Just like Yahoo! Answers, someone asks a question, and then anyone who knows the answer will reply to them.

In the end, users can browse the previously uploaded cases. The users can check the content of uploaded cases and keep waiting for doctors' replies to those cases.

## Development Information
* Tool : **Android Studio**
* Environment : **Windows**
* Programming Language : **Java**

## Database
I am using the following two functions of **Firebase** to store pictures and user data.
| Function  | Introduction | Picture |
| ------------- |---------------|---------------|
| **Cloud Storage**      | Cloud Storage for Firebase is built for app developers who need to store and serve user-generated content, such as photos or videos.     | ![image](https://github.com/brian415112/Detect_AI/blob/11ed8ab1d807c71fe85520452fbe68ae124739a7/image/Cloud%20Storage.png)     |
| **Cloud Firestore**      | Use flexible, scalable NoSQL cloud database to store and sync data for client- and server-side development.     | ![image](https://github.com/brian415112/Detect_AI/blob/11ed8ab1d807c71fe85520452fbe68ae124739a7/image/Cloud%20Firestore.png)     |

## Screenshot
<img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/1.jpg" width="24%"> <img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/1-1.jpg" width="24%"> <img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/1-2.jpg" width="24%"> <img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/2.jpg" width="24%">

<img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/3.jpg" width="24%"> <img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/3-1.jpg" width="24%"> <img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/3-2.jpg" width="24%"> <img src="https://github.com/brian415112/Detect_AI/blob/b6f5651dcb9decf20dfa581e0e78bb373fce5b61/image/screenshot/4.jpg" width="24%">

## Hardware
| Name | Picture |
| :-------------: | :---------------: |
| **Wemos D1 mini** | <img src="https://github.com/brian415112/Detect_AI/blob/c4edc6258a520a884e919b0048140393b3b732a3/image/hardware/Wemos%20D1%20mini.jpg" width="200">|
| **AD8232**<br>*Use this to detect ECG.* | <img src="https://github.com/brian415112/Detect_AI/blob/c4edc6258a520a884e919b0048140393b3b732a3/image/hardware/AD8232.jpg" width="200">|
| **MAX30100**<br>*Use this to detect blood oxygen and pulse.* | <img src="https://github.com/brian415112/Detect_AI/blob/c4edc6258a520a884e919b0048140393b3b732a3/image/hardware/MAX30100.jpg" width="200">|
