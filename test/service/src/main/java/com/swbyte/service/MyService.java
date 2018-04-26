package com.swbyte.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import static com.swbyte.service.IMyAidlInterface.*;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return new MyStub();
    }

    class MyStub extends Stub{

        @Override
        public int add(int i, int j) throws RemoteException {
            return i+j;
        }
    }

}
