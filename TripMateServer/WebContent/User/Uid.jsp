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
	String nickname = request.getParameter("nickname");
	String uid = request.getParameter("uid");	
	System.out.println("nickname : "+nickname + "uid : " + uid);
	UserDAO userDAO = new UserDAO();
	String result = userDAO.uidUpdate(uid,nickname);
	
	System.out.println("uidupate : " + result);
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("uid-update",jArray);
	out.print(jsonMain.toJSONString());
	out.flush();
%>
	
