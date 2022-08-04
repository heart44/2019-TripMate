package com.example.tripmate.Share;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.tripmate.Ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpShareInfoGetList extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class sendTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strings) {
            try {
                Ip i = new Ip();
                String ip = i.getIP();
                String url = "http://" + ip + ":8080/TripMateServer/Share/shareInfoView.jsp";

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
                    //String sendmsg = "pagenumber=" + strings[0];
                    //os.write(sendmsg);
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

                    Log.i("planRoute", result);

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
