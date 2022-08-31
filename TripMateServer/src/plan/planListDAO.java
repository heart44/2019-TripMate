package plan;

import java.sql.*;
import java.util.*;

import board.Board;

public class planListDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public planListDAO() throws ClassNotFoundException, SQLException {
		try {
			String URL = "jdbc:mysql://localhost:3306/triptest?serverTimezone=Asia/Seoul&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
			String id = "root";
			String password = "heart44";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, id, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getIndex() {

		String SQL = "SELECT COUNT(*) FROM tripplan";

		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;// 첫 번째 컬럼인 경우
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("\nERROR: 코드 생성 오류\n");
		return 0;
	}
	
	public String createCode() {

		int serial = getIndex();

		String suffix = String.format("%03d", serial);
		String planListCode = "PLANLIST-" + suffix;

		//System.out.println(planListCode + "코드 생성됨");

		return planListCode;
	}
	
	//플랜리스트
	public ArrayList<planList> ListView(String userCode) {
		String SQL2 = "select plan_code, plan_place, plan_title, date_format(trip_start_date, '%y/%m/%d'), date_format(trip_end_date, '%y/%m/%d') from tripplan where delete_state = 0 and user_code = ? order by plan_code desc";
		ArrayList<planList> planlist = new ArrayList<planList>();
		try {
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1,userCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				planList plan = new planList();
				
				plan.setPlanCode(rs.getString(1));
				plan.setPlanPlace(rs.getString(2));
				plan.setPlanTitle(rs.getString(3));
				plan.setStartDate(rs.getString(4));
				plan.setEndDate(rs.getString(5));
				planlist.add(plan);				
				
			}
		}catch (Exception e) {
	         e.printStackTrace();
		}
		return planlist;
	}
	
	//섹션별비용
	public ArrayList<costList> CostList1(String planCode) {
		String SQL2 = "select * from tripcost where delete_state = 0 and plan_code = ? and bank_statement_date = '0000-00-00 00:00:00' order by trip_cost_code asc";
		ArrayList<costList> costList = new ArrayList<costList>();
		try {
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1,planCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				costList cost = new costList();
				
				cost.setCostCode(rs.getString(1));
				cost.setPlanCode(rs.getString(2));
				cost.setCostTitle(rs.getString(3));
				cost.setCostCategory(rs.getInt(4));
				cost.setCostType(rs.getInt(5));
				cost.setCostDate(rs.getString(6));
				cost.setCostPrice(rs.getInt(7));
				costList.add(cost);				
				
			}
		}catch (Exception e) {
	         e.printStackTrace();
		}
		return costList;
	}
	
	//여행준비(날짜로설정)빼고
	public ArrayList<costList> CostList2(String planCode) {
		String SQL2 = "select trip_cost_code, plan_code, money_title, money_category, money_type, date_format(bank_statement_date, '%y/%m/%d'), bank_statement_cost from tripcost where delete_state = 0 and plan_code = ? and bank_statement_date not in('0000-00-00 00:00:00') order by trip_cost_code asc";
		ArrayList<costList> costList = new ArrayList<costList>();
		try {
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1,planCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				costList cost = new costList();
				
				cost.setCostCode(rs.getString(1));
				cost.setPlanCode(rs.getString(2));
				cost.setCostTitle(rs.getString(3));
				cost.setCostCategory(rs.getInt(4));
				cost.setCostType(rs.getInt(5));
				cost.setCostDate(rs.getString(6));
				cost.setCostPrice(rs.getInt(7));
				costList.add(cost);				
				
			}
		}catch (Exception e) {
	         e.printStackTrace();
		}
		return costList;
	}
	
	public int getIndex2() {

		String SQL = "SELECT COUNT(*) FROM tripcost";

		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;// 첫 번째 컬럼인 경우
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("\nERROR: 코드 생성 오류\n");
		return 0;
	}
	
	public String createCode2() {

		int serial = getIndex2();

		String suffix = String.format("%03d", serial);
		String costCode = "COST-" + suffix;

		//System.out.println(planListCode + "코드 생성됨");

		return costCode;
	}
}
