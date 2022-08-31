<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "user.UserDAO" %>
<%@ page import = "board.Board" %>
<%@ page import = "board.BoardDAO" %>
<%@ page import = "DateTime.format" %>

<%
	request.setCharacterEncoding("UTF-8");
	String boardCode = request.getParameter("boardcode");
	
	BoardDAO boardDAO = new BoardDAO();
	

	String result = boardDAO.delete(boardCode);
	System.out.println("delete : " + result);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("delete", jArray);
	out.print(jsonMain.toJSONString());
	out.flush(); 
%>
	
