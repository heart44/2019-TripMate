package com.example.tripmate.TourAPI;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class TripInfoAPI  {
	
	String serviceKey = "RPU4UbJL2yNOUQMdaXZ10221FLiSFEY3FbuGX4eBbEQQMMTOy6LtUh4auKv4hhgV5OspcFK0QJGwqBI2xEk52A%3D%3D";

	public ArrayList<TripDataInfo> getTripInfo(String inputKeyword) {

		/* 서비스키는 필요한 경우 아니면 손대지 말 것. */
		/*
		 * contentTypeID : 12(관광지), 14(문화시설), 15(축제/공연/행사), 25(여행코스), 28(레포츠), 32(숙박),
		 * 38(쇼핑), 39(음식)
		 */

		String contentTypeId = "12";
		String keyword = inputKeyword;

		ArrayList<TripDataInfo> dto = new ArrayList();

		try {
			String keywords = URLEncoder.encode(keyword, "UTF-8");
			String urlStr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?serviceKey="
					+ serviceKey
					+ "&MobileApp=AppTest&MobileOS=ETC&pageNo=1&numOfRows=100&listYN=Y&arrange=A&contentTypeId="
					+ contentTypeId+"&keyword="+keywords +"&_type=json";

			URL url = new URL(urlStr);
			Log.i("TDB",urlStr);

			/* 건들이지 않아도 되는 부분 */

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Content-type", "application/json");

			/* 버퍼에 데이터 담아 데이터 확인하는 부분 */
			BufferedReader br;

			Log.i("eeeeeeeeeeeeeeeeeeee",urlStr);

			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
			}
			Log.i("wwwwwwwwww",urlStr);
			StringBuilder sb = new StringBuilder();
			String line;
			Log.i("1111111",urlStr);
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			br.close();
			conn.disconnect();
			Log.i("222222",urlStr);

			/* 파싱하는 부분 */

			Log.i("lgogogogogogo",sb.toString());

			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONObject parResponse = (JSONObject) jsonObject.get("response");
			JSONObject parBody = (JSONObject) parResponse.get("body");
			JSONObject parItems = (JSONObject) parBody.get("items");
			JSONArray InfoArray = (JSONArray) parItems.get("item");

			for (int i = 0; i < InfoArray.length(); i++) {
				TripDataInfo info = new TripDataInfo();
				JSONObject tripObject = (JSONObject) InfoArray.get(i);

				if(tripObject.isNull("addr1")) {
					info.setAddress2("주소가 없음");
				} else {

					info.setAddress1(tripObject.getString("addr1"));
				}

				if(tripObject.isNull("addr2")) {
					info.setAddress2("주소가 없음");
				} else {

					info.setAddress2(tripObject.getString("addr2"));
				}

				if(tripObject.isNull("firstimage")) {
					info.setImgURL("이미지가 없음");
				} else {
					info.setImgURL(tripObject.getString("firstimage"));
				}

				if(tripObject.isNull("mapx")) {
					info.setMapX("X값 위치 없음");
				} else {
					info.setMapX(tripObject.getString("mapx"));
				}

				if(tripObject.isNull("mapy")) {
					info.setMapY("Y값 위치 없음");
				} else {
					info.setMapY(tripObject.getString("mapy"));
				}

				if(tripObject.isNull("title")) {
					info.setTitle("지정관광지명 없음");
				} else {
					info.setTitle(tripObject.getString("title"));
				}

				dto.add(info);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	

}
