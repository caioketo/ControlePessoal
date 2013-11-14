package com.keto.controlepessoal.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by developer on 13/11/13.
 */
public class OnAlarmReciever  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context);

        context.startService(new Intent(context, AppService.class));
    }
}