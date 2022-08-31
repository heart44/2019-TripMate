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
<%@ page import = "java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	String nickname = request.getParameter("nickname");
	UserDAO userDAO = new UserDAO();
	String usercode = userDAO.codeSearch(nickname);
	
	planListDAO listDAO = new planListDAO();
	
	ArrayList<planList> list= new ArrayList<planList>();
	list = listDAO.ListView(usercode);
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	for(int i = 0 ; i < list.size();i++){
		JSONObject sObject = new JSONObject();
		sObject.put("plancode",list.get(i).getPlanCode());
		sObject.put("place",list.get(i).getPlanPlace());
		sObject.put("title",list.get(i).getPlanTitle());
		sObject.put("start",list.get(i).getStartDate());
		sObject.put("end",list.get(i).getEndDate());
				
		jArray.add(sObject);
	}
	jsonMain.put("addlist",jArray);
	
	System.out.println("plan list show");
	out.print(jsonMain.toJSONString());
	out.flush(); 

			
%>
	
