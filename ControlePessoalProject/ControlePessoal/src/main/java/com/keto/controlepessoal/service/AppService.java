package com.keto.controlepessoal.service;

import android.content.Intent;

/**
 * Created by developer on 13/11/13.
 */
public class AppService extends WakefulIntentService {
    public AppService() {
        super("AppService");
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        //Checar WS por alertas
    }
}
