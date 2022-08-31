package DateTime;

import java.sql.Date;

public class format {
	//2019년10월31일 형식으로 들어옴
	
	public format(){}
	
	public String changeDateFormat(String d) {
		String date = d;
		
		date = date.replace("년", "-");
		date = date.replace("월", "-");
		date = date.replace("일", " ");
		date = date.replace("시", ":");
		date = date.replace("분", ":00");
		
		return date;
	}
	
	public String changeDateFormat2(String d) {
		String date1 = d;
		
		String date = date1.substring(0,16) + " ~ " + date1.substring(32,37); //매칭 스타트 년원일 시간 분 + 매칭 엔드 시간분
		return date;
	}
	
	public String changeDateFormat3(String d) {
		String date1 = "20"+d;
		
		String date = date1.substring(0,16) + " ~ " + date1.substring(32,37); //매칭 스타트 년원일 시간 분 + 매칭 엔드 시간분
		return date;
	}
}