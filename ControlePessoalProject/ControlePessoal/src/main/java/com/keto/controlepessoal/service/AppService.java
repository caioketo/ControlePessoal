package com.keto.controlepessoal.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.keto.controlepessoal.MainActivity;
import com.keto.controlepessoal.R;
import com.keto.controlepessoal.classes.AlertaProduto;
import com.keto.controlepessoal.util.Communicator;

import org.json.JSONObject;

/**
 * Created by developer on 13/11/13.
 */
public class AppService extends WakefulIntentService {
    public static AppService service;

    public AppService() {
        super("AppService");
        service = this;
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        //Checar WS por alertas
        try {
            String jsonAlerta = new Communicator().execute("SERVICE", "Alertas", "GET").get();
            AlertaProduto alerta = new AlertaProduto(new JSONObject(jsonAlerta));
            CreateNotification(alerta, true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void CreateNotification(AlertaProduto alerta, boolean service) {
        Context ctx;
        if (service) {
            ctx = AppService.service;
        }
        else {
            ctx = MainActivity.ctx;
        }
        RemoteViews remoteViews = new RemoteViews(ctx.getPackageName(),
                R.layout.notification_produto);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                ctx).setSmallIcon(R.drawable.ic_launcher).setContent(
                remoteViews);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(ctx, MainActivity.class);
        // The stack builder object will contain an artificial back stack for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btnAddListaCompra, resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
    }


    public static void CreateNotification2(AlertaProduto alerta) {
        String temp = "Produto: " + alerta.Produto.Descricao +
                " \n Quantidade Atual: " + ((Double)alerta.Produto.Quantidade).toString();
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
        //PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) MainActivity.ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(alerta.AlertaId, mBuilder.build());
    }
}
