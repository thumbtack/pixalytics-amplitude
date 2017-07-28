package com.thumbtack.pixalytics.amplitude.proxy;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.amplitude.api.Amplitude;
import com.amplitude.api.Identify;
import com.pixable.pixalytics.core.Event;
import com.pixable.pixalytics.core.Screen;
import com.pixable.pixalytics.core.proxy.PlatformProxy;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AmplitudeProxy implements PlatformProxy {

    private final Application mApplication;

    private final String mApiKey;

    private final Map<String, Object> mCommonProperties = new HashMap<>();

    public AmplitudeProxy(Application application, String apiKey) {
        mApplication = application;
        mApiKey = apiKey;
    }

    @Override
    public void onApplicationCreate(Context context) {
        Amplitude.getInstance().initialize(context, mApiKey).enableForegroundTracking(mApplication);
    }

    @Override
    public void onSessionStart(Context context) {
        // Happens automatically
    }

    @Override
    public void onSessionFinish(Context context) {
        // Happens automatically
    }

    @Override
    public void addCommonProperty(String name, @NonNull Object value) {
        mCommonProperties.put(name, value);
    }

    @Override
    public void addCommonProperties(Map<String, Object> commonProperties) {
        mCommonProperties.putAll(commonProperties);
    }

    @Override
    public void clearCommonProperty(String name) {
        mCommonProperties.remove(name);
    }

    @Override
    public void clearCommonProperties() {
        mCommonProperties.clear();
    }

    @Override
    public void addUserProperty(@NonNull String name, @NonNull Object value) {
        Amplitude.getInstance().identify(new Identify().set(name, value));
    }

    @Override
    public void trackEvent(Event event) {
        final Map<String, Object> allProperties = event.getProperties();
        allProperties.putAll(mCommonProperties);

        Amplitude.getInstance().logEvent(event.getName(), new JSONObject(allProperties));
    }

    @Override
    public void trackScreen(Screen screen) {
        // Not relevant for Amplitude
    }

    @Override
    public void trackSocial(String network, String action, String target) {
        // Not relevant for Amplitude
    }

    @Override
    public void trackRevenue(String product, double revenue) {
        Amplitude.getInstance().logRevenue(product, 1, revenue);
    }

    @Override
    public void flush() {
        Amplitude.getInstance().uploadEvents();
    }

    @Override
    public String getIdentifier() {
        return Amplitude.getInstance().getUserId();
    }

    @Override
    public void setIdentifier(String identifier) {
        Amplitude.getInstance().setUserId(identifier);
    }
}
