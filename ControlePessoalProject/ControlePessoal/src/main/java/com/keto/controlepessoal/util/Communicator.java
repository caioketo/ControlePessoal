package com.keto.controlepessoal.util;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.keto.controlepessoal.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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

    public static String getURL() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.ctx);
        return settings.getString("host", "http://jangadaserver.no-ip.info/Geral/");
    }
    @Override
    protected String doInBackground(String... Urls) {
        String url = getURL() + Urls[0];
        InputStream is = null;
        String result = "";
        if (Urls[1] == "GET") {
            // HTTP GET
            try {
                String data = "";
                HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
                HttpGet httpget;
                if (Urls.length > 2) {
                    httpget = new HttpGet(url + "?" + Urls[2]);
                }
                else {
                    httpget = new HttpGet(url);
                }
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
                HttpResponse response;
                if (data == "JSON") {
                    response = makeJSONRequest(url, Urls[3]);
                }
                else {
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
                    response = httpclient.execute(httppost);
                }
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


    public static HttpResponse makeJSONRequest(String path, String json) throws Exception
    {
        //instantiates httpclient to make request
        DefaultHttpClient httpclient = new DefaultHttpClient();

        //url with the post data
        HttpPost httpost = new HttpPost(path);

        //passes the results to a string builder/entity
        StringEntity se = new StringEntity(json);

        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        return httpclient.execute(httpost);
    }
}
