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

	ArrayList<ShareModel> shareList = new ArrayList<ShareModel>();
	ShareDAO dao = new ShareDAO();

	shareList.addAll(dao.getList());

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	for(int i = 0 ; i < shareList.size();i++){
		
		JSONObject sObject = new JSONObject();
		
		sObject.put("planCode",shareList.get(i).getPlanCode());
		sObject.put("locationTitle",shareList.get(i).getLocationTitle());
		sObject.put("shareTitle",shareList.get(i).getShareTitle());
		sObject.put("content",shareList.get(i).getContents());
	
		
		jArray.add(sObject);
	}
	
	jsonMain.put("list",jArray);
	
	System.out.println("shareList get reuslt");
	out.print(jsonMain.toJSONString());
	out.flush(); 
	
%>