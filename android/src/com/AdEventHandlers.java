package com;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Gravity; // Added import for Gravity
import android.widget.FrameLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Arrays;

public class AdEventHandlers {

    private static final String TAG = "AdEventHandlers";
    private static InterstitialAd mInterstitialAd;
    private static AdView mAdView;
    private static boolean bannerAdLoaded = false;
    private static boolean interstitialAdLoaded = false;
    private static boolean interstitialAdShowed = false;
    private static boolean interstitialAdDismissed = false;
    private static PowerManager.WakeLock wakeLock;
    private static boolean showBannerAd = false;

     public static void acquireWakeLock(Activity activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "MyApp::WakeLock");
        wakeLock.acquire();
        Log.d(TAG, "WakeLock acquired");
    }

    // Method to release wake lock
    public static void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            Log.d(TAG, "WakeLock released");
        }
    }

    // Configure test devices
    private static AdRequest getAdRequest() {
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
            .setTestDeviceIds(Arrays.asList())     // at the moment it is empty if you want you can add the test device ids
            .build();
        MobileAds.setRequestConfiguration(requestConfiguration);

        return new AdRequest.Builder().build();
    }

    // Interstitial Ad Methods
    public static void loadInterstitialAd(Activity activity, String interstitialAdId) {
        new Handler(Looper.getMainLooper()).post(new Runnable(){
        @Override
            public void run() {
            Log.i(TAG, "Loading Interstitial Ad with ID: ");
            AdRequest adRequest = getAdRequest();

            InterstitialAd.load(activity, interstitialAdId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        interstitialAdLoaded = true;
                        Log.i(TAG, "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                Log.d(TAG, "Ad was clicked.");
                                releaseWakeLock();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                                interstitialAdDismissed = true;   // Set the flag
                                loadInterstitialAd(activity, interstitialAdId);
                                releaseWakeLock();  // Release wake lock when ad is dismissed
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                                releaseWakeLock();

                            }

                            @Override
                            public void onAdImpression() {
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d(TAG, "Ad showed fullscreen content.");
                                interstitialAdShowed = true;
                                acquireWakeLock(activity);
                            }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Log.d(TAG, loadAdError.toString());
                    mInterstitialAd = null;

                }
            });
        }
    });
    }

    public static void showInterstitialAd(Activity activity) {
        Log.i(TAG, "Showing Interstitial Ad");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if
                    (mInterstitialAd != null) {
                    interstitialAdDismissed = false;
                    mInterstitialAd.show(activity);
                    acquireWakeLock(activity);
                }
                else {
                    Log.d(TAG, "The interstitial ad wasn't ready yet.");
                }
            }
        });
    }

    public static boolean isInterstitialAdDismissed() {
        return interstitialAdDismissed;
    }

    public static void resetInterstitialAdDismissedFlag() {
        new Handler(Looper.getMainLooper()).post(() -> {
            interstitialAdDismissed = false;
            Log.d(TAG, "Interstitial Ad Dismissed Flag Reset.");
        });
    }

    public boolean isInterstitialAdLoaded(){
        return interstitialAdLoaded;
    }

    public void resetInterstitialAdLoadedFlag(){
        interstitialAdLoaded = false;
    }

    public boolean interstitialAdShowed(){
        return interstitialAdShowed;
    }

    public void resetInterstitialAdShowedFlag(){
        interstitialAdShowed = false;
    }

    public boolean isBannerAdLoaded(){
        return bannerAdLoaded;
    }

    // Banner Ad Methods
    public static void setupBannerAd(Activity activity, String bannderAdId) {
        Log.i(TAG, "Setting Up Banner Ad with ID: ");

        // Ensure this code runs on the main (UI) thread
        new Handler(Looper.getMainLooper()).post(() -> {
            mAdView = new AdView(activity);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(bannderAdId);

            FrameLayout layout = (FrameLayout) activity.findViewById(android.R.id.content);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

            layout.addView(mAdView, params);

            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    Log.i(TAG, "Banner Ad Loaded successfully");
                    bannerAdLoaded = true;
                    if(!showBannerAd){
                        mAdView.setVisibility(View.GONE);
                    }else{
                        mAdView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(TAG, "Banner Ad Failed to Load: " + adError.getMessage());
                }
            });

            Log.i(TAG, "Loading Banner Ad...");
            mAdView.loadAd(new AdRequest.Builder().build());
        });
    }

    public static void loadBannerAd() {
        if (mAdView != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (mAdView.getParent() != null) {
                    AdRequest adRequest = getAdRequest();
                    mAdView.loadAd(adRequest);
                    Log.d(TAG, "Banner Ad is Loaded.");
                } else {
                    Log.w(TAG, "Banner AdView is not added to the layout.");
                }
            });
        } else {
            Log.d(TAG, "Banner AdView is not initialized.");
        }
    }

    public static void showBannerAd() {
        if (mAdView != null && mAdView.getParent() != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (bannerAdLoaded) {
                    mAdView.setVisibility(View.VISIBLE);
                    showBannerAd = true;
                    mAdView.bringToFront();
                    Log.d(TAG, "Banner Ad is now visible.");
                } else {
                    Log.d(TAG, "Banner Ad is not loaded yet.");
                }
            });
        } else {
            Log.e(TAG, "Banner AdView is not initialized or added to the layout.");
        }
    }


    public static void hideBannerAd() {
        if (mAdView != null && mAdView.getParent() != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Banner AdView current visibility: " + mAdView.getVisibility());
                    mAdView.setVisibility(View.GONE);
                    showBannerAd = false;
                    Log.d(TAG, "Now BannerAd is Hidden.");
                }
            });
        } else {
            Log.e(TAG, "Banner AdView is either null or not added to the layout.");
        }
    }

}
