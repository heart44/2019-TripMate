<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="share.ShareDAO"%>
<%@ page import="share.ShareModel"%>
<%@ page import="DateTime.format"%>
<%@ page import="java.util.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	format dateformat = new format();
	
	ShareDAO dao = new ShareDAO();


	String planCode = request.getParameter("plancode");

	String result = dao.delete(planCode);
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