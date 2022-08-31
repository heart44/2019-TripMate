<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "org.json.simple.*" %>
<%@ page import = "user.UserDAO" %>
<%@ page import = "board.Board" %>
<%@ page import = "board.BoardDAO" %>
<%@ page import = "DateTime.format" %>
<%@ page import = "java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	
	String pageNumber1 = request.getParameter("pagenumber");
	int pageNumber = Integer.parseInt(pageNumber1);
	
	BoardDAO boardDAO = new BoardDAO();
	
	ArrayList<Board> list= new ArrayList<Board>();
	list = boardDAO.getList(pageNumber);
	
	UserDAO userDAO = new UserDAO();
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	for(int i = 0 ; i < list.size();i++){
		JSONObject sObject = new JSONObject();
		sObject.put("boardcode",list.get(i).getBoardCode());
		sObject.put("nickname",userDAO.codeToNicknameSearch(list.get(i).getUserCode()));
		sObject.put("destination",list.get(i).getDestination());
		sObject.put("gender",list.get(i).getGender());
		sObject.put("minage",list.get(i).getMinage());
		sObject.put("maxage",list.get(i).getMaxage());
		sObject.put("date",list.get(i).getMatchingstartDatetime().substring(0,10)); //년월일만나오도록 자르기
		sObject.put("purpose",list.get(i).getPurpose());
		
		jArray.add(sObject);
	}
	jsonMain.put("show",jArray);
	
	System.out.println("board list show");
	out.print(jsonMain.toJSONString());
	out.flush(); 
%>
	
