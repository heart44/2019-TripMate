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
<%@ page import = "java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	String plancode = request.getParameter("plancode");
	System.out.println(plancode);
	
	planListDAO listDAO = new planListDAO();
	
	ArrayList<costList> list2= new ArrayList<costList>();
	list2 = listDAO.CostList1(plancode);
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	for(int i = 0 ; i < list2.size();i++){
		JSONObject sObject = new JSONObject();
		sObject.put("costcode",list2.get(i).getCostCode());
		sObject.put("plancode",list2.get(i).getPlanCode());
		sObject.put("title",list2.get(i).getCostTitle());
		sObject.put("category",list2.get(i).getCostCategory());
		sObject.put("type",list2.get(i).getCostType());
		sObject.put("date",list2.get(i).getCostDate());
		sObject.put("price",list2.get(i).getCostPrice());

		jArray.add(sObject);
	}
	jsonMain.put("costlist",jArray);
	
	System.out.println("cost list show");
	out.print(jsonMain.toJSONString());
	out.flush(); 

			
%>
	
