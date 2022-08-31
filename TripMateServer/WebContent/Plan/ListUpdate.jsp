<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "plan.planList" %>
<%@ page import = "plan.planListDAO" %>
<%@ page import = "user.UserDAO" %>

<%
	request.setCharacterEncoding("UTF-8");
	
	String plancode = request.getParameter("plancode");
	String place = request.getParameter("place");
	String title = request.getParameter("title");
	String start = request.getParameter("start");
	String end = request.getParameter("end");
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	String result1 = null;
	try {
		String URL = "jdbc:mysql://localhost:3306/triptest?serverTimezone=Asia/Seoul&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
		String id = "root";
		String password = "heart44";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(URL, id, password);
		
		String SQL = "UPDATE tripplan SET plan_place = ?, plan_title = ?, trip_start_date = ?, trip_end_date = ? WHERE plan_code = ?";
		
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, place);
		pstmt.setString(2, title);
		pstmt.setString(3, start);
		pstmt.setString(4, end);
		pstmt.setString(5, plancode);
		int result = pstmt.executeUpdate();
		if (result >= 0)
			result1 = "success";
		else
			result1 = "db-error";
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	System.out.println("update : " + result1);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();

	jObject.put("msg", result1);
	jArray.add(0, jObject);
	jsonMain.put("update", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); 

			
%>
	
