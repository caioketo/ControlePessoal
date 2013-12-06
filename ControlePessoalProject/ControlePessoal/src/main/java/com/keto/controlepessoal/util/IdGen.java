package com.keto.controlepessoal.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.keto.controlepessoal.MainActivity;

/**
 * Created by developer on 29/11/13.
 */
public class IdGen {
    public static int AlertaId = 0;

    public static int GenID() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.ctx);
        AlertaId = settings.getInt("alertaId", 0);
        AlertaId++;
        settings.edit().putInt("alertaId", AlertaId).commit();
        return AlertaId;
    }

}
