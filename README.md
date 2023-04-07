## LokOS Automation Script
- This is an automation script for creating SHG Data for Lok-OS Training Android Application. 
- This is made using Appium Testing Framework.

## Technologies Used
- Android Framework
- Appium Automation Framework
- Appium Inspector
- Charles Proxy and MITM Proxy for Packet Analysis

## Steps to Setup the Script
### Installing and Setting Up Appium and This Repository
- First clone this repository
- Now install [`Node.js`](https://nodejs.org/en/download) , if not already installed.
- Now open terminal and run the following command
```
npm install -g appium
```
- Install [`Intellij Idea`](https://www.jetbrains.com/idea/download/) , if not already installed.
- Now open the cloned repository with Intellij Idea.
- Open pom.xml file and resolve all dependencies with following button

![image](https://user-images.githubusercontent.com/76804249/230651164-6556cf7c-73bb-4257-8d47-669f1abb6dd4.png)

### Installing ADB on System
- Download this [`Zip File`](https://dl.google.com/android/repository/platform-tools-latest-windows.zip)
- Unzip this file in `C:\Program Files\Platform Tools`
- Now add this folder to `Path` in `Environmental Variables` (for windows).
- With this ADB is installed on the system and accesible via the Terminal.

### Turning on USB Debugging in Android Device
- First enable `Developer Options` on your Android Device. This process might be different for different `OEM's`
- Now enable `USB Debugging` from Developer Options.
- `Install via USB` and `USB debugging (Security Settings)` also need to be enable on certain `OEM's` like `Xiaomi`.
<img src="https://user-images.githubusercontent.com/76804249/230653196-127b04c8-ac3b-49eb-b5f5-bcc661d43aec.jpg" width="400" height="800">

### Installing LokOS-Training App
- Install `LokOS-traning` application from playstore.
- Login with your credentials.
- Set the login PIN to `1234`

### Running the Script
- Connect the device to PC via USB or over [`WiFi`](https://help.famoco.com/developers/dev-env/adb-over-wifi/)
- Now just open `LokOS_Automation` file and run it.

![image](https://user-images.githubusercontent.com/76804249/230651928-707c1787-e0b4-43e0-9cc0-bf4584c819f3.png)
- Now just enter the number of SHG's to be created along with number of members in each SHG and let the magic happen.

### ❌❌ Note ❌❌
```
The above script relies on UI elements for simulating the input.  
It works perfectly fine on Xiaomi Devices and Samsung Devices(refer to Line 254
in LokOS_Automation File). 
However it can be tweaked to work with any OEM's device with very minor
changes in 2-3 Lines. You can contact me in case you want to get it tweaked
for your OEM, or you can use Appium Inspector to identify the UI component yourself.
```

