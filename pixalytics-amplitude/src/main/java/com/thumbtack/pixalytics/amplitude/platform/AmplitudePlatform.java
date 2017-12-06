package com.thumbtack.pixalytics.amplitude.platform;

import android.support.annotation.NonNull;

import com.pixable.pixalytics.core.platform.Platform;
import com.pixable.pixalytics.core.proxy.PlatformProxy;

import thumbtack.com.pixalytics.amplitude.R;

public class AmplitudePlatform extends Platform {

    public AmplitudePlatform(
            @NonNull final String id, @NonNull final PlatformProxy amplitudeProxy
    ) {
        super(id, R.drawable.pixalytics__tracking_toast_amplitude, amplitudeProxy);
    }
}
