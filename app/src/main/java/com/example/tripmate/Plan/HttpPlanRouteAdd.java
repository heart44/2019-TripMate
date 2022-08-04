package com.example.tripmate.Plan;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.tripmate.Ip;
import com.example.tripmate.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPlanRouteAdd extends Activity {

    static String nickName;
    static String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class SendTask extends AsyncTask<String, Void, String> {

        public String doInBackground(String... strings) {
            try {
                Ip i = new Ip();
                String ip = i.getIP();
                String url = "http://" + ip + ":8080/TripMateServer/Plan/PlanRouteInsert.jsp";

                URL urlText = null;

                try {
                    urlText = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) urlText.openConnection();

                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Accept-Charset", "UTF-8");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");



                    OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                    String sendmsg = "userid=" + strings[0] + "&plancode=" + strings[1] +
                                     "&plandate=" + strings[2] + "&title=" + strings[3] + "&locationx=" + strings[4] +
                                     "&locationy=" + strings[5] + "&memo=" + strings[6] + "&index=" + strings[7] + "&state=" + strings[8];

                    Log.i("23423423423",sendmsg);
                    os.write(sendmsg);
                    os.flush();
                    os.close();

                    int retCode = conn.getResponseCode();

                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();

                    while ((line = br.readLine()) != null) {
                        response.append(line);
                        response.append(' ');
                    }
                    br.close();

                    String result = response.toString();
                    System.out.println(result);
                    String receiveMsg = parsedData(result);

                    return receiveMsg;

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public String parsedData(String recvMsg){
        JSONArray jarray = null;
        try {
            jarray = new JSONObject(recvMsg).getJSONArray("update");
            JSONObject jsonObject = jarray.getJSONObject(0);
            String receiveMsg = jsonObject.getString("msg");
            return receiveMsg;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
