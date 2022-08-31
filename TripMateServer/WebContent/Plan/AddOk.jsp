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
	
	String nickname = request.getParameter("nickname");
	UserDAO userDAO = new UserDAO();
	String usercode = userDAO.codeSearch(nickname);
	String place = request.getParameter("place");
	String title = request.getParameter("title");
	String start = request.getParameter("start");
	String end = request.getParameter("end");
	
	planListDAO listDAO = new planListDAO();
	String code = listDAO.createCode();
	
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
		
		String SQL = "INSERT INTO tripplan VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, code);
		pstmt.setString(2, usercode);
		pstmt.setString(3, place);
		pstmt.setString(4, title);
		pstmt.setString(5, start);
		pstmt.setString(6, end);
		pstmt.setInt(7, 0);
		int result = pstmt.executeUpdate();
		if (result >= 0)
			result1 = "success";
		else
			result1 = "db-error";
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	System.out.println("add : " + result1);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();

	jObject.put("msg", result1);
	jArray.add(0, jObject);
	jsonMain.put("add", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); 

			
%>
	
