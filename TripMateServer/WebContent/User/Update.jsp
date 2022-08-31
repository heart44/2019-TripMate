<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "user.User" %>
<%@ page import = "user.UserDAO" %>

<%
	request.setCharacterEncoding("UTF-8");
	String currentNickname = request.getParameter("currentnickname");
	String nextNickname = request.getParameter("nextnickname");
	String password = request.getParameter("password");
	
	UserDAO userDAO = new UserDAO();
	String result = userDAO.update(currentNickname,nextNickname,password);
	System.out.println("update : " + result);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("update", jArray);
	out.print(jsonMain.toJSONString());
	out.flush();
%>
	
