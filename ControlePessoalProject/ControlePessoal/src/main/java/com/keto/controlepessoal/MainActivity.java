package com.keto.controlepessoal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.keto.controlepessoal.activities.MercadoAct;
import com.keto.controlepessoal.service.OnAlarmReciever;
import com.keto.controlepessoal.util.IntentIntegrator;
import com.keto.controlepessoal.util.IntentResult;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private static final int PERIOD=300000;
    final String PREFS_NAME = "ControlePessoalPref";
    ArrayList<String> prods = new ArrayList<String>();
    public static Context ctx;
    public static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstRun();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private void firstRun() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("firstRun", true)) {
            AlarmManager mgr=(AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
            Intent i=new Intent(ctx, OnAlarmReciever.class);
            PendingIntent pi=PendingIntent.getBroadcast(ctx, 0,
                    i, 0);

            mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime()+60000,
                    PERIOD,
                    pi);

            settings.edit().putBoolean("firstRun", false).commit();
        }
    }

    @Override
    public void onActivityResult(int request, int result, Intent i) {
        IntentResult scan=IntentIntegrator.parseActivityResult(request,
                result,
                i);

        if (scan!=null) {
            Toast.makeText(this, scan.getContents(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "SCAN NULL", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Button btnProd = (Button)rootView.findViewById(R.id.btnProdutos);
            btnProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ctx, MercadoAct.class));
                }
            });
            ((Button)rootView.findViewById(R.id.btnScan)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity());
                    integrator.initiateScan();
                }
            });
            return rootView;
        }
    }
}
