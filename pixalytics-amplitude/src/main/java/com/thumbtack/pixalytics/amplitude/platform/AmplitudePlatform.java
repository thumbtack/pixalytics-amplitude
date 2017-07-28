package com.thumbtack.pixalytics.amplitude.platform;

import com.pixable.pixalytics.core.platform.Platform;
import com.pixable.pixalytics.core.proxy.PlatformProxy;

import thumbtack.com.pixalytics.amplitude.R;

public class AmplitudePlatform extends Platform {

    public AmplitudePlatform(String id, PlatformProxy amplitudeProxy) {
        super(id, R.drawable.pixalytics__tracking_toast_amplitude, amplitudeProxy);
    }
}
