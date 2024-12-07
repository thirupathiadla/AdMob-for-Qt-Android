<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="git@github.com:thirupathiadla/AdMob-for-Qt-Android.git">
  </a>

<h3 align="center">AdMob for Qt for Android</h3>
  <p align="center">
  </p>
</div>


<!-- ABOUT THE PROJECT -->
## About The Project

AdMob Manager is a sample project that demonstrates how to integrate Google AdMob ads into a Qt Android Application using native Java code and the QJniObject class. This project supports both Banner Ads and Interstitial Ads, allowing seamless ad display management directly from C++ code.



<!-- GETTING STARTED -->
## Getting Started

Download all the content and open the content in QT Creator.

Select the Kit - 

 * for Android Emulator.
    ```sh
        Adnroid Qt6.8.0 Clang x86_64
    ```
  
 * for Android Device.
    ```sh
        Adnroid Qt6.8.0 Clang arm64-v8a
    ```


 * You need to update AndroidManifest.xml with your app id.
    ```sh
     <meta-data android:name="com.google.android.gms.ads.APPLICATION_ID" android:value="ca-app-pub-3940256099942544~3347511713"/>
    ```

* You need to update the BannerAd id and Interstitial ad Id in admobbridge.cpp with your own ids.
  ```sh
    QString bannerAdId = "ca-app-pub-3940256099942544/6300978111";
    QString interstitialAdId = "ca-app-pub-3940256099942544/1033173712";
  ```

* Update the build.gradle file with below information.
  ```sh
  dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])
    implementation 'androidx.core:core:1.13.1'
    implementation 'com.google.android.gms:play-services-ads:23.1.0'
    implementation 'com.google.firebase:firebase-ads:23.1.0'
  }
   ```
* replace google-services.json in android/src directory with your own json file.






















