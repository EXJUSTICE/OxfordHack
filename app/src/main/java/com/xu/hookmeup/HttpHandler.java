package com.xu.hookmeup;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Omistaja on 19/11/2016.
 * http://stackoverflow.com/questions/32549360/best-practice-to-get-json-from-url-in-api-23
 * https://www.learn2crack.com/2013/10/android-asynctask-json-parsing-example.html
 *https://www.tutorialspoint.com/android/android_json_parser.htm
 */

public class HttpHandler{

    private static final  String TAG = HttpHandler.class.getSimpleName();
    //constructor
    public HttpHandler(){

    }

    public String makeServiceCall( String reqUrl) {
        String response = null;

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //read response

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException");
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException");

        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }

        return response;
    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while ((line = reader.readLine())!=null){
                sb.append(line).append('\n');

            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        return sb.toString();
    }
}
