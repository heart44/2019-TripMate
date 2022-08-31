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
	String title = request.getParameter("title");
	String category = request.getParameter("category");
	int category2 = Integer.parseInt(category);
	String type = request.getParameter("type");
	int type2 = Integer.parseInt(type);
	String date = request.getParameter("date");
	String price = request.getParameter("price");
	int price2 = Integer.parseInt(price);
	
	planListDAO listDAO = new planListDAO();
	String code = listDAO.createCode2();
	
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
		
		String SQL = "INSERT INTO tripcost VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, code);
		pstmt.setString(2, plancode);
		pstmt.setString(3, title);
		pstmt.setInt(4, category2);
		pstmt.setInt(5, type2);
		pstmt.setString(6, date);
		pstmt.setInt(7, price2);
		pstmt.setInt(8, 0);
		int result = pstmt.executeUpdate();
		if (result >= 0)
			result1 = "success";
		else
			result1 = "db-error";
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	System.out.println("costadd : " + result1);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();

	jObject.put("msg", result1);
	jArray.add(0, jObject);
	jsonMain.put("costadd", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); 

			
%>
	
