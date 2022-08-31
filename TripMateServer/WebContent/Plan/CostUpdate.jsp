<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "plan.costList" %>
<%@ page import = "plan.planListDAO" %>
<%@ page import = "user.UserDAO" %>

<%
	request.setCharacterEncoding("UTF-8");

	String costcode = request.getParameter("costcode");
	String title = request.getParameter("title");
	String category = request.getParameter("category");
	int category2 = Integer.parseInt(category);
	String type = request.getParameter("type");
	int type2 = Integer.parseInt(type);
	String date = request.getParameter("date");
	String price = request.getParameter("price");
	int price2 = Integer.parseInt(price);
	
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
		
		String SQL = "UPDATE tripcost SET money_title = ?, money_category = ?, money_type = ?, bank_statement_date = ?, bank_statement_cost = ? WHERE trip_cost_code = ?";
		
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, title);
		pstmt.setInt(2, category2);
		pstmt.setInt(3, type2);
		pstmt.setString(4, date);
		pstmt.setInt(5, price2);
		pstmt.setString(6, costcode);
		int result = pstmt.executeUpdate();
		if (result >= 0)
			result1 = "success";
		else
			result1 = "db-error";
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	System.out.println("costupdate : " + result1);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();

	jObject.put("msg", result1);
	jArray.add(0, jObject);
	jsonMain.put("costupdate", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); 

			
%>
	
