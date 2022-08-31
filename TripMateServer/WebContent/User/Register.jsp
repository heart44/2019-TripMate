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
	String nickname = request.getParameter("nickname");
	String age1 = request.getParameter("age");
	int age = Integer.parseInt(age1);
	String gender1 = request.getParameter("gender");
	int gender = Integer.parseInt(gender1);
	/* String interest1 = request.getParameter("interest1");
	String interest2 = request.getParameter("interest2");
	String interest3 = request.getParameter("interest3"); */
	String email = request.getParameter("email");

	UserDAO userDAO = new UserDAO();
	User user = new User(id, password, nickname, age, gender, email);

	String result = userDAO.register(user);
	System.out.println("register : " + result);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("register", jArray);
	out.print(jsonMain.toJSONString());
	out.flush();
%>
	
