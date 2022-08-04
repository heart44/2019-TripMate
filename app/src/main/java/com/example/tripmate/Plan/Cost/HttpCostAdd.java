package com.example.tripmate.Plan.Cost;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.tripmate.Ip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpCostAdd extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    class SendTask extends AsyncTask<String, Void, String> {
        public String doInBackground(String... strings) {
            try {
                Ip a = new Ip();
                String ip = a.getIP();
                String url = "http://" + ip + ":8080/TripMateServer/Plan/CostAdd.jsp";
                URL obj = null;
                try {
                    obj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Accept-Charset", "UTF-8");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    String sendMsg = "plancode=" + strings[0] + "&title=" + strings[1] + "&category=" + strings[2] + "&type=" + strings[3] + "&date=" + strings[4] + "&price=" + strings[5];
                    System.out.println(sendMsg);
                    osw.write(sendMsg);
                    osw.flush();
                    osw.close();

                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                        buffer.append(' ');
                    }
                    reader.close();

                    String result = buffer.toString();
                    System.out.println(result);
                    String receiveMsg = parsedData(result);
                    return receiveMsg;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } /*catch (JSONException e) {
                    e.printStackTrace();
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public String parsedData(String recvMsg){
        JSONArray jarray = null;
        try {
            jarray = new JSONObject(recvMsg).getJSONArray("costadd");
            JSONObject jsonObject = jarray.getJSONObject(0);
            String receiveMsg = jsonObject.getString("msg");
            return receiveMsg;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
