package com.anjan.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Anjan Debnath on 3/30/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class ServerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SimpleMultiply();
    }
}
