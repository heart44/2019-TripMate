package com.example.tripmate.User;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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
import java.net.URL;

public class HttpUserRegister extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class sendTask extends AsyncTask<String, Void, String> {
        public String doInBackground(String... strings) {
            try {
                Ip i = new Ip();
                String ip = i.getIP();
                String url = "http://"+ip+":8080/TripMateServer/User/Register.jsp";
               //String url = "http://192.168.214.146:8080/TripMateServer/User/Register.jsp";
                //122.199.81.73
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

                    OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                    String sendmsg = "id=" + strings[0]+"&password="+strings[1]+"&nickname="+strings[2]+"&age="+strings[3]
                            +"&gender="+strings[4]+"&email="+strings[5];
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
            jarray = new JSONObject(recvMsg).getJSONArray("register");
            JSONObject jsonObject = jarray.getJSONObject(0);
            String receiveMsg = jsonObject.getString("msg");
            return receiveMsg;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}