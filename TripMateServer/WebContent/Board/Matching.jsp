<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="user.UserDAO"%>
<%@ page import="board.Board"%>
<%@ page import="board.BoardDAO"%>
<%@ page import="DateTime.format"%>
<%@ page import="java.util.*"%>

<%
	request.setCharacterEncoding("UTF-8");

	request.setCharacterEncoding("UTF-8");
	String nickname = request.getParameter("nickname");
	UserDAO userDAO = new UserDAO();
	String usercode = userDAO.codeSearch(nickname);
	int userAge = userDAO.usercodeToAge(usercode); // 매칭을 원하는 작성자의 나이
	String destination = request.getParameter("destination");
	String gender1 = request.getParameter("gender");
	int gender = Integer.parseInt(gender1); //상대방의 성별
	String minage1 = request.getParameter("minage");
	int minage = Integer.parseInt(minage1);
	String maxage1 = request.getParameter("maxage");
	int maxage = Integer.parseInt(maxage1);

	String date1 = request.getParameter("date");
	String starttime1 = request.getParameter("starttime");
	System.out.println("starttime1 : " + starttime1);
	format dateformat = new format();
	String startdatetime = dateformat.changeDateFormat(date1 + starttime1);
	System.out.println("startdatetime : " + startdatetime);
	String purpose = request.getParameter("purpose");
	
	
	
	BoardDAO boardDAO = new BoardDAO();
	
	ArrayList<Board> list = new ArrayList<Board>();
	list = boardDAO.getMatchingList(destination, gender, minage, maxage, userAge, startdatetime, purpose);
 
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	for (int i = 0; i < list.size(); i++) {
		JSONObject sObject = new JSONObject();
		sObject.put("boardcode", list.get(i).getBoardCode());
		sObject.put("nickname", userDAO.codeToNicknameSearch(list.get(i).getUserCode()));
		sObject.put("destination", list.get(i).getDestination());
		sObject.put("gender", list.get(i).getGender());
		sObject.put("minage", list.get(i).getMinage());
		sObject.put("maxage", list.get(i).getMaxage());
		sObject.put("date", list.get(i).getMatchingstartDatetime().substring(0, 10)); //년월일만나오도록 자르기
		sObject.put("purpose", list.get(i).getPurpose());
		
		jArray.add(sObject);
	}
	
	jsonMain.put("matching", jArray);
	System.out.println("matching list size : " + list.size());
	System.out.println("matching list show");
	out.print(jsonMain.toJSONString());
	out.flush();
%>

