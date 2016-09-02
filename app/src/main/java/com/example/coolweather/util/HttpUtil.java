package com.example.coolweather.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by LeiYang on 2016/9/1 0001.
 */

public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallBackListener httpCallBackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection ;
                try {
                    URL url = new URL(address);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(1000*10);
                    httpURLConnection.setConnectTimeout(1000*10);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    if ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.i("info","ininininninininininininin");
                    Log.i("info",stringBuilder.toString());

                    if (httpCallBackListener != null) {
                        httpCallBackListener.onFinish(stringBuilder.toString());
                    }

                } catch (MalformedURLException e) {
                    Log.i("info","MalformedURLException.onError()");
                    httpCallBackListener.onError(e);
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i("info","IOException.onError()");
                    httpCallBackListener.onError(e);
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
