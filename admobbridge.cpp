#include "admobbridge.h"
#include <QJniObject>
#include <QVBoxLayout>
#include <QMessageBox>

AdMobBridge::AdMobBridge(QWidget *parent) : QWidget(parent) {

    // Cache Activity
    cacheActivity();

    registerNativeMethod();

    QString bannerAdId = "ca-app-pub-3940256099942544/6300978111";
    QString interstitialAdId = "ca-app-pub-3940256099942544/1033173712";

    setupBannerAd(bannerAdId);
    loadBannerAd();
    loadInterstitialAd(interstitialAdId);

    // Create Buttons
    interstitialAdButton = new QPushButton("Show Interstitial Ad", this);
    bannerAdButton = new QPushButton("Show Banner Ad", this);
    hideBannerAdButton = new QPushButton("Hide Banner Ad", this);

    // Customize Buttons Appearance
    QString buttonStyle = R"(
        QPushButton {
            background-color: #2196F3;
            color: white;
            font-size: 16px;
            padding: 8px 16px;
            border-radius: 6px;
        }
        QPushButton:hover {
            background-color: #1976D2;
        }
        QPushButton:pressed {
            background-color: #0D47A1;
        }
    )";

    interstitialAdButton->setStyleSheet(buttonStyle);
    bannerAdButton->setStyleSheet(buttonStyle);
    hideBannerAdButton->setStyleSheet(buttonStyle);

    // Connect Buttons to Slots
    connect(interstitialAdButton, &QPushButton::clicked, this, &AdMobBridge::onShowInterstitialAdClicked);
    connect(bannerAdButton, &QPushButton::clicked, this, &AdMobBridge::onShowBannerAdClicked);
    connect(hideBannerAdButton, &QPushButton::clicked, this, &AdMobBridge::onHideBannerAdClicked);

    // Manually Set Positions
    bannerAdButton->setGeometry(60, 220, 150, 50);     // (x, y, width, height)
    hideBannerAdButton->setGeometry(250, 220, 150, 50); // Positioned next to bannerAdButton
    interstitialAdButton->setGeometry(140, 350, 200, 50); // Positioned below the banner buttons
}

void AdMobBridge::registerNativeMethod()
{
    const JNINativeMethod methods[] = {{"notifyInterstitialAdDismissed", "()V", reinterpret_cast<void *>(AdMobBridge::onInterstitialAdDismissed)}};

    QJniEnvironment env;
    env.registerNativeMethods("com/AdEventHandlers", methods, 1);

}

void AdMobBridge::onInterstitialAdDismissed()
{
    qDebug()<<"From Qt C++, Interstitial Ad is Dismissed";

}


void AdMobBridge::onShowBannerAdClicked()
{
    showBannerAd();
}

void AdMobBridge::onShowInterstitialAdClicked()
{
    showInterstitialAd();
}

void AdMobBridge::onHideBannerAdClicked()
{
    hideBannerAd();
}

void AdMobBridge::cacheActivity() {
    activity = QJniObject::callStaticObjectMethod(
        "org/qtproject/qt/android/QtNative",
        "activity",
        "()Landroid/app/Activity;"
        );

    if (!activity.isValid()) {
        qWarning() << "Failed to cache Android Activity.";
    }
}

void AdMobBridge::setupBannerAd(const QString &bannerAdId) {
    if (!activity.isValid()) {
        qWarning() << "Activity is invalid.";
        return;
    }

    QJniObject adId = QJniObject::fromString(bannerAdId);

    QJniObject::callStaticMethod<void>(
        "com/AdEventHandlers",
        "setupBannerAd",
        "(Landroid/app/Activity;Ljava/lang/String;)V",
        activity.object(),
        adId.object<jstring>()
        );
}

void AdMobBridge::loadBannerAd() {
    QJniObject::callStaticMethod<void>("com/AdEventHandlers", "loadBannerAd", "()V");
}

void AdMobBridge::showBannerAd() {
    QJniObject::callStaticMethod<void>("com/AdEventHandlers", "showBannerAd", "()V");
}

void AdMobBridge::hideBannerAd() {
    QJniObject::callStaticMethod<void>("com/AdEventHandlers", "hideBannerAd", "()V");
}

void AdMobBridge::loadInterstitialAd(const QString &interstitialAdId) {
    if (!activity.isValid()) {
        qWarning() << "Activity is invalid.";
        return;
    }

    QJniObject adId = QJniObject::fromString(interstitialAdId);

    QJniObject::callStaticMethod<void>(
        "com/AdEventHandlers",
        "loadInterstitialAd",
        "(Landroid/app/Activity;Ljava/lang/String;)V",
        activity.object(),
        adId.object<jstring>()
        );
}

void AdMobBridge::showInterstitialAd() {
    if (!activity.isValid()) {
        qWarning() << "Activity is invalid.";
        return;
    }
    QJniObject::callStaticMethod<void>(
        "com/AdEventHandlers",
        "showInterstitialAd",
        "(Landroid/app/Activity;)V",
        activity.object()
        );
}
