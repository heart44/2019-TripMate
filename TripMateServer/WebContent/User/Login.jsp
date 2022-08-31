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
	String id = request.getParameter("id");
	String password = request.getParameter("password");
	System.out.println("id : "+id+" password : "+password);
	UserDAO userDAO = new UserDAO();
	String result = userDAO.login(id,password);
	System.out.println("Login : "+result);
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	JSONObject jObject1 = new JSONObject();
	JSONObject jObject2 = new JSONObject();
	JSONObject jObject3 = new JSONObject();

	jObject1.put("msg", result);
	jObject2.put("email", userDAO.emailSearch(id));
	jObject3.put("nickname",userDAO.idToNicknameSearch(id));
	
	jArray.add(0, jObject1);
	jArray.add(0,jObject2);
	jArray.add(0,jObject3);
	
	jsonMain.put("Login",jArray);
	System.out.println(jsonMain);
	out.print(jsonMain.toJSONString());
	out.flush();
%>
	