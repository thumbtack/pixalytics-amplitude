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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AmplitudeProxy implements PlatformProxy {

    @NonNull
    private final Application mApplication;

    @NonNull
    private final String mApiKey;

    @NonNull
    private final Map<String, Object> mCommonProperties = new ConcurrentHashMap<>();

    public AmplitudeProxy(@NonNull final Application application, @NonNull final String apiKey) {
        mApplication = application;
        mApiKey = apiKey;
    }

    @Override
    public void onApplicationCreate(@NonNull final Context context) {
        Amplitude.getInstance().initialize(context, mApiKey).enableForegroundTracking(mApplication);
    }

    @Override
    public void onSessionStart(@NonNull final Context context) {
        // Happens automatically
    }

    @Override
    public void onSessionFinish(@NonNull final Context context) {
        // Happens automatically
    }

    @Override
    public void addCommonProperty(@NonNull final String name, @NonNull final Object value) {
        mCommonProperties.put(name, value);
    }

    @Override
    public void addCommonProperties(@NonNull final Map<String, Object> commonProperties) {
        mCommonProperties.putAll(commonProperties);
    }

    @Override
    public void clearCommonProperty(@NonNull final String name) {
        mCommonProperties.remove(name);
    }

    @Override
    public void clearCommonProperties() {
        mCommonProperties.clear();
    }

    @Override
    public void addUserProperty(@NonNull final String name, @NonNull final Object value) {
        Amplitude.getInstance().identify(new Identify().set(name, value));
    }

    @Override
    public void trackEvent(@NonNull final Event event) {
        final Map<String, Object> allProperties = event.getProperties();
        allProperties.putAll(mCommonProperties);

        Amplitude.getInstance().logEvent(event.getName(), new JSONObject(allProperties));
    }

    @Override
    public void trackScreen(@NonNull final Screen screen) {
        // Not relevant for Amplitude
    }

    @Override
    public void trackSocial(
            @NonNull final String network,
            @NonNull final String action,
            @NonNull final String target
    ) {
        // Not relevant for Amplitude
    }

    @Override
    public void trackRevenue(@NonNull final String product, final double revenue) {
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
    public void setIdentifier(@NonNull final String identifier) {
        Amplitude.getInstance().setUserId(identifier);
    }
}
