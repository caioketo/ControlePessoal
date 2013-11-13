package com.keto.controlepessoal.util;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by developer on 01/11/13.
 */
public class Communicator extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... Urls) {
        String url = Urls[0];
        InputStream is = null;
        String result = "";
        if (Urls[1] == "GET") {
            // HTTP GET
            try {
                HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
                HttpGet httpget = new HttpGet(url);
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch(Exception e) {
                return null;
            }

            // Read response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch(Exception e) {
                return null;
            }
            return result;
        }
        else if (Urls[1] == "POST") {

        }
        return "";
    }
}
