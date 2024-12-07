
<br />
<div align="center">
  <a href="git@github.com:thirupathiadla/AdMob-for-Qt-Android.git">

  </a>

<h3 align="center">AdMob for Qt for Android</h3>

  <p align="center">
    project_description
    <br />
    <a href="https://github.com/github_username/repo_name"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="git@github.com:thirupathiadla/AdMob-for-Qt-Android.git">View Demo</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>


<!-- GETTING STARTED -->
## Getting Started

Download all the content and open the content in QT Creator.
Select the Kit Adnroid Qt6.8.0 Clang x86_64


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


<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>





