package com.keto.controlepessoal.util;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
            // HTTP POST
            try {
                String data = Urls[2];
                String[] datas = data.split("&");
                HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
                HttpPost httppost = new HttpPost(url);
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                for (int i = 0; i < datas.length; i++) {
                    String[] keyPar = datas[i].split("=");
                    nameValuePairs.add(new BasicNameValuePair(keyPar[0], keyPar[1]));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
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
        return "";
    }
}
