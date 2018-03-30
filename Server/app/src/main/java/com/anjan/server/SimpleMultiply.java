package com.anjan.server;

import android.os.RemoteException;

/**
 * Created by Anjan Debnath on 3/30/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class SimpleMultiply extends IMultiply.Stub {
    @Override
    public int multiply(int a, int b) throws RemoteException {
        return a*b;
    }
}
