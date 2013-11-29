package com.keto.controlepessoal.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.keto.controlepessoal.MainActivity;
import com.keto.controlepessoal.R;
import com.keto.controlepessoal.classes.AlertaProduto;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONObject;

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
        try {
            String jsonAlerta = new Communicator().execute("Alertas", "GET").get();
            AlertaProduto alerta = new AlertaProduto(new JSONObject(jsonAlerta));
            CreateNotification(alerta);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void CreateNotification(AlertaProduto alerta) {
        String temp = "Produto: " + alerta.Produto.Descricao +
                " \\n Quantidade Atual: " + ((Double)alerta.Produto.Quantidade).toString();
        //Intent resultIntent = new Intent(MainActivity.act, MainActivity.class);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.ctx)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLights(0xFF0000FF,100,3000)
                .setVibrate(new long[] {100, 1000, 300, 1000})
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Produto Acabando")
                .setContentText(temp);
        //resultIntent.putExtra("FromNot", true);
        //resultIntent.putExtra("MesaId", MesaId);
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder.setAutoCancel(true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.ctx);
        stackBuilder.addParentStack(MainActivity.class);
        //stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) MainActivity.ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(alerta.AlertaId, mBuilder.build());
    }
}
