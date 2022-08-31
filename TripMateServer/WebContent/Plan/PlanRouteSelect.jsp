<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="plan.TripPlanRouteListDAO"%>
<%@ page import="plan.TripPlanRouteListModel"%>
<%@ page import="java.util.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userID = request.getParameter("userid");
	String planCode = request.getParameter("plancode");
	
	System.out.println(userID);
	
	
	
	TripPlanRouteListDAO dao = new TripPlanRouteListDAO();
	
	ArrayList<TripPlanRouteListModel> routeList = new ArrayList<TripPlanRouteListModel>();
	
	String userCode = dao.getUserCode(userID);
	
	routeList = dao.getRouteList(userCode, planCode);
	
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	for(int i = 0 ; i < routeList.size();i++){
		
		JSONObject sObject = new JSONObject();
		
		sObject.put("userCode",routeList.get(i).getUser_code());
		sObject.put("planCode",routeList.get(i).getPlan_code());
		//sObject.put("listCode",routeList.get(i).getList_code());
		sObject.put("planDate",routeList.get(i).getPlan_date());
		sObject.put("title",routeList.get(i).getTitle());
		sObject.put("locationx",routeList.get(i).getLocationx());
		sObject.put("locationy",routeList.get(i).getLocationy());
		sObject.put("index",routeList.get(i).getIndex());
		sObject.put("memo",routeList.get(i).getMemo());
		
		jArray.add(sObject);
	}
	
	jsonMain.put("list",jArray);
	
	System.out.println("planRouteList get reuslt");
	out.print(jsonMain.toJSONString());
	out.flush(); 
	
%>