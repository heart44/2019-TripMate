<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="plan.TripPlanRouteListDAO"%>
<%@ page import="DateTime.format"%>
<%@ page import="plan.TripPlanRouteListModel"%>
<%@ page import="java.util.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	format dateformat = new format();
	
	TripPlanRouteListDAO dao = new TripPlanRouteListDAO();
	format fm = new format();
	
	String userid = request.getParameter("userid");
	
	//유저코드 가져오기
	String userCode = dao.getUserCode(userid);
	String usercode = userCode;
	System.out.println("1111111111"+usercode);
	String plancode = request.getParameter("plancode");
	System.out.println("2222222"+plancode);
	String plandate = request.getParameter("plandate");
	//plandate = fm.changeDateFormat3(plandate);
	System.out.println("333333333"+plandate);
	String title = request.getParameter("title");
	System.out.println("44444444444"+plandate);
	double locationx = Double.parseDouble(request.getParameter("locationx"));
	double locationy = Double.parseDouble(request.getParameter("locationy"));
	System.out.println("5555555555"+locationy);
	String memo = "";
	if(request.getParameter("memo") == null) {
		memo = "";
	} else {
		memo = request.getParameter("memo");	
	}
	
	
	int index = Integer.parseInt(request.getParameter("index"));	
	int state = Integer.parseInt(request.getParameter("state"));	
	
	TripPlanRouteListModel model = 
    new TripPlanRouteListModel(usercode,plancode,plandate,title,locationx,locationy,memo,index,state);

	String result = dao.tripRouteInsert(model);
	System.out.println("addtrip : " + result);

	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject1 = new JSONObject();

	jObject1.put("msg", result);
	jArray.add(0, jObject1);
	jsonMain.put("addtrip", jArray);
	out.print(jsonMain.toJSONString());
	out.flush();
%>
