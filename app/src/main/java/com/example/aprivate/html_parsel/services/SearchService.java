package com.example.aprivate.html_parsel.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.aprivate.html_parsel.network.RequestCreator;

public class SearchService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RequestCreator request = new RequestCreator("Iphone+7+");
        request.execute();
//        Intent i = new Intent(this, RequestCreator.class);
//        startActivity(i);
        return super.onStartCommand(intent, flags, startId);
    }
}
