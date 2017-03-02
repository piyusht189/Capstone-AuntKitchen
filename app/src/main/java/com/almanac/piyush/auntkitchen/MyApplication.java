package com.almanac.piyush.auntkitchen;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import app.AnalyticsTrackers;

/**
 * Created by 1415044 on 03-03-2017.
 */

public class MyApplication extends Application {
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsTrackers.initialize(this);
    }

    /**
     * Gets the default {@link Tracker} for this {@link MyApplication}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}