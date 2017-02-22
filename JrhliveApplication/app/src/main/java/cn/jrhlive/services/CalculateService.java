package cn.jrhlive.services;

import android.app.Service;
import android.content.Intent;
import android.jrhlive.com.jrhliveapplication.ICalculate;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * desc:
 * Created by jiarh on 16/7/29 14:48.
 */
public class CalculateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    Binder mBinder  = new ICalculate.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1+num2;
        }
    };
}
