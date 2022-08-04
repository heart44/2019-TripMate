package com.example.tripmate.Plan.Cost;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tripmate.Ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpCostList2 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public class sendTask extends AsyncTask<String, Void, String> {
        public String doInBackground(String... strings) {
            try {
                Ip a = new Ip();
                String ip = a.getIP();
                String url = "http://"+ip+":8080/TripMateServer/Plan/CostList2.jsp";
                //String url = "http://192.168.214.146:8080/TripMateServer/Board/ShowList.jsp";

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
                    String sendmsg = "plancode=" + strings[0];
                    System.out.println("플랜코드드드드"+sendmsg);
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
                    System.out.println("결과"+result);
                    return result;

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
