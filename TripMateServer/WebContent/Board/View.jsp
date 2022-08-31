<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "org.json.simple.JSONArray" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%@ page import = "org.json.simple.parser.JSONParser"%>
<%@ page import = "org.json.simple.parser.ParseException" %>
<%@ page import = "org.json.simple.*" %>
<%@ page import = "user.UserDAO" %>
<%@ page import = "user.User" %>
<%@ page import = "board.Board" %>
<%@ page import = "board.BoardDAO" %>
<%@ page import = "DateTime.format" %>
<%@ page import = "java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	
	String boardCode = request.getParameter("boardcode");
	
	
	BoardDAO boardDAO = new BoardDAO();
	Board board = boardDAO.getBoard(boardCode);
	UserDAO userDAO = new UserDAO();
	User user = userDAO.getUser(board.getUserCode());
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	
	
		JSONObject sObject = new JSONObject();
		
		sObject.put("destination",board.getDestination());
		sObject.put("content",board.getContent());
		String gender = null;
		if(board.getGender() == 0){
			gender = "남";
		}
		else{
			gender = "여";
		}
		
		String minage = Integer.toString(board.getMinage());
		String maxage = Integer.toString(board.getMaxage());
		sObject.put("matching-gender-age",minage + " ~ " + maxage +"세 " +gender);
		
		
		format datetimeformat = new format();
		String send = board.getMatchingstartDatetime() + board.getMatchingendDatetime();
		String date = datetimeformat.changeDateFormat2(send);
		sObject.put("matching-date",date);
		sObject.put("writing-date",board.getNoticeDatetime().substring(0,19)); 
		sObject.put("purpose",board.getPurpose());
		sObject.put("writer",user.getUserNickname());
		
		String usergender = null;
		if(user.getUserGender() == 0){
			usergender = "남";
		}
		else{
			usergender = "여";
		}
		String userage = Integer.toString(user.getUserAge());
		sObject.put("writer-gender-age",userage +"세 "+ usergender);
		
		jArray.add(sObject);
	
	jsonMain.put("view",jArray); 
	
	System.out.println("board view : " + jsonMain.toJSONString());
	out.print(jsonMain.toJSONString());
	out.flush(); 
%>
	
