package com.example.tripmate.Location;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LocationInfoAPI {

    String serviceKey = "RPU4UbJL2yNOUQMdaXZ10221FLiSFEY3FbuGX4eBbEQQMMTOy6LtUh4auKv4hhgV5OspcFK0QJGwqBI2xEk52A%3D%3D";

    public ArrayList<LocationDataInfo> getLocationInfo(String contentTypeId, double mapx, double mapy, int radius) {

        /* 서비스키는 필요한 경우 아니면 손대지 말 것. */
        /*
         * contentTypeID : 12(관광지), 14(문화시설), 15(축제/공연/행사), 25(여행코스), 28(레포츠), 32(숙박),
         * 38(쇼핑), 39(음식)
         */

        //String contentTypeId = "12";
        ArrayList<LocationDataInfo> dto = new ArrayList<>();

        try {
            Log.i("TDB","ddddddddddddddddddddddddd");
            String urlStr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?serviceKey="
                    + serviceKey
                    + "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&contentTypeId="
                    + contentTypeId + "&mapX=" + mapx + "&mapY=" + mapy + "&radius=" + radius + "&_type=json";

            URL url = new URL(urlStr);
            Log.i("TDB",urlStr);

            /* 건들이지 않아도 되는 부분 */

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            /* 버퍼에 데이터 담아 데이터 확인하는 부분 */

            BufferedReader br;
            StringBuilder sb;

            /*
             * Response Code: 200 나오면 정상적으로 출력된 것임. 만약 제대로 결과 값 나오지 않으면 현재 정상작동 상태이나 해당 키워드에
             * 정보 없는 것임.
             */

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }

            sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            Log.i("first","지금출력되고있다");
            br.close();
            conn.disconnect();

            /* 파싱하는 부분 */

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONObject parResponse = (JSONObject) jsonObject.get("response");
            JSONObject parBody = (JSONObject) parResponse.get("body");
            JSONObject parItems = (JSONObject) parBody.get("items");
            JSONArray InfoArray = (JSONArray) parItems.get("item");

            for (int i = 0; i < InfoArray.length(); i++) {

                LocationDataInfo info = new LocationDataInfo();
                JSONObject tripObject = (JSONObject) InfoArray.get(i);

                if(tripObject.isNull("addr1")) {
                    info.setAddress1("주소가 없음");
                } else {

                    info.setAddress1(tripObject.getString("addr1"));
                }

                if(tripObject.isNull("addr2")) {
                    info.setAddress2("주소가 없음");
                } else {

                    info.setAddress2(tripObject.getString("addr2"));
                }

                if(tripObject.isNull("firstimage")) {
                    info.setFirstimage("이미지가 없음");
                } else {
                    info.setFirstimage(tripObject.getString("firstimage"));
                }

                if(tripObject.isNull("firstimage2")) {
                    info.setFirstimage2("이미지가 없음");
                } else {
                    info.setFirstimage2(tripObject.getString("firstimage2"));
                }

                if(tripObject.isNull("mapx")) {
                    info.setMapX(0);
                } else {
                    info.setMapX(tripObject.getDouble("mapx"));
                }

                if(tripObject.isNull("mapy")) {
                    info.setMapY(0);
                } else {
                    info.setMapY(tripObject.getDouble("mapy"));
                }

                if(tripObject.isNull("title")) {
                    info.setTitle("지정관광지명 없음");
                } else {
                    info.setTitle(tripObject.getString("title"));
                }

                if(tripObject.isNull("tel")) {
                    info.setTel("");
                } else {
                    info.setTel(tripObject.getString("tel"));
                }

                if(tripObject.isNull("dist")) {
                    info.setDist(0);
                } else {
                    info.setDist(tripObject.getInt("dist"));
                }

                dto.add(info);


            }



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("현재 정상 출력 되지 않고 있습니다.");
        }

        return dto;
    }

}