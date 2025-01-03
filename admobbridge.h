#ifndef ADMOBBRIDGE_H
#define ADMOBBRIDGE_H

#include <QWidget>
#include <QPushButton>
#include <QVBoxLayout>
#include <QJniObject>
#include <QTimer>

class AdMobBridge: public QWidget
{
    Q_OBJECT
public:

    explicit AdMobBridge(QWidget *parent = nullptr);

    static void onInterstitialAdDismissed();

private slots:
    void onShowBannerAdClicked();
    void onShowInterstitialAdClicked();
    void onHideBannerAdClicked();
    void checkInterstitialAdDismissed();

signals:
    void interstitialAdDismissed();

private:
    void setupBannerAd(const QString &bannerAdId);
    void loadBannerAd();
    void showBannerAd();
    void hideBannerAd();
    void loadInterstitialAd(const QString &interstitialAdId);
    void showInterstitialAd();
    void cacheActivity();

    QPushButton *bannerAdButton;
    QPushButton *interstitialAdButton;
    QPushButton *bannerAdSetup;
    QPushButton *hideBannerAdButton;
    QVBoxLayout *layout;
    QJniObject activity;

    QTimer *checkAdDismissedTimer;
};

#endif // ADMOBBRIDGE_H
