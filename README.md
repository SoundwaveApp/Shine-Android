# Shine Android SDK #

This guide will walk you through adding Shine to your Android application.

##Getting started##
----------

To begin using Shine you will need to acquire your unique **DEVELOPER_KEY**. Currently these keys are given out on an ad-hoc basis by [Soundwave](http://soundwave.com). Please contact Soundwave if you have not received your key or would like to enquire about receiving one.

###Requirements###

Shine on Android currently supports 2.3 (API 9) and above.

##Integrating Shine##
----------

In your app’s `build.gradle` file add the following to your *repositories* block:


```css
repositories {
    maven { url 'http://repo.soundwave.com' }
}
```

Then add the following to your dependencies block:

```css
dependencies {
    compile 'com.soundwave:shine-android-sdk:0.0.+'
}
```

###Mandatory Permissions###

The following permissions are defined in the source code of the Shine SDK. Which means that by using Shine your app will ask for these permissions even if you don’t have them on your `AndroidManifest`.


```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
```

###Optional Permissions###

To make the most of the Shine SDK you should also ask for the following permissions. Note that these are optional and you can choose not to use some of them if they don’t fit your app’s needs. Doing so will prevent the SDK from working at it’s full capacity but Shine will still do as most as it can.

```xml
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<!-- Required to collect device’s contacts -->
<uses-permission android:name="android.permission.READ_CONTACTS" />
<!-- Required to collect the music library -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<!-- Required to collect device’s location -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

###Initialising Shine###

Initialize Shine by calling the following in the `onCreate()` method of your Application class:


```java
Shine.initialize(this, "YOUR_DEVELOPER_KEY");
```

###Hooking information###

On successful completion of your authentication method in your login activity you will need to register your user.

```java
private void onSuccessfulLogin() {
    // Your code to handle successful login ...

    // Registering with Shine is easy. For best results, use a unique userId if you have one.
    Shine.registerIdentifiedUser(this, new Registration()
         .withUserId("123456")
         .withEmail("john@email.com")
         .withFirstName("John")
         .withMiddleName("M.")
         .withLastName("Smith"));
}
```

##Enabling Logs##
----------

Should you have any issues while using Shine, you can enable and send the logs to our developers. They will be happy to help you out. In order to enable the logs simply call the following method before you initialize Shine:


```java
Shine.enableLogging();
Shine.initialize(this, "YOUR_DEVELOPER_KEY");
```
