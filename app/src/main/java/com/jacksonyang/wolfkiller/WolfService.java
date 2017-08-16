package com.jacksonyang.wolfkiller;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class WolfService extends Service {
    private DownloadADBinder downloadADBinder;

    public WolfService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return downloadADBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("WolfService","启动成功!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("WolfService","启动成功!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WolfService","已关闭!");
    }

    class DownloadADBinder extends Binder{

        public void startDownload(){
            Log.d("WolfService","程序已经开始进行恶意操作");
        }


    }
}
