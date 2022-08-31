package plan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TripPlanRouteListDAO {

	private Connection conn;
	private ResultSet rs;

	public TripPlanRouteListDAO() {
		try {

			String dbURL = "jdbc:mysql://localhost:3306/triptest?&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "heart44";

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String makeCode() {
		String idx = "List-";
		int num = getNext();
		if (num == -1)
			return "error";
		String code = idx + num;
		return code;
	}

	// 게시글 갯수 세기
	public int getNext() {

		String SQL = "SELECT list_code FROM tripplanlist ORDER BY list_code DESC";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;// 첫 번째 게시물인 경우
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:게시물 인덱스 오류\n");
		return 0;
	}

	// userId로 userCode 가져오기
	public String getUserCode(String id) {
		String SQL = "SELECT user_code FROM user WHERE user_id = ?";
		String userCode = "";

		System.out.println("dddd," + id);

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				userCode = rs.getString("user_code");
			}
			System.out.println(userCode);
			return userCode;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:게시물 인덱스 오류\n");

		return userCode;

	}

	// 만들어진 여행 리스트 가져오기
	public ArrayList<TripPlanRouteListModel> getRouteList(String userCode, String planCode) {

		String SQL = "SELECT user_code, plan_code, date_format(plan_date, '%y/%m/%d'), title, locationx, locationy, index_number, memo FROM tripplanlist WHERE delete_state = 0 AND user_code = ? and plan_code = ?";
		ArrayList<TripPlanRouteListModel> list = new ArrayList<TripPlanRouteListModel>();

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userCode);
			pstmt.setString(2, planCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				TripPlanRouteListModel model = new TripPlanRouteListModel();	
				  
				model.setUser_code(rs.getString(1));
				model.setPlan_code(rs.getString(2));	
				model.setPlan_date(rs.getString(3));
				System.out.println(rs.getString(3));
				model.setTitle(rs.getString(4));
				model.setLocationx(rs.getDouble(5));
				model.setLocationy(rs.getDouble(6));
				model.setIndex(rs.getInt(7));
				model.setMemo(rs.getString(8));

				list.add(model);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:게시물 인덱스 오류\n");

		return list;

	}

	// 여행 리스트 추가하기

	public String tripRouteInsert(TripPlanRouteListModel tripRoute) {

		String SQL = "INSERT INTO tripplanlist VALUES(?,?,?,?,?,?,?,?,?)";

		  
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, tripRoute.getUser_code());
			pstmt.setString(2, tripRoute.getPlan_code());
			pstmt.setString(3, tripRoute.getPlan_date());
			System.out.println("된건지 확인하기");
			pstmt.setString(4, tripRoute.getTitle());
			pstmt.setDouble(5, tripRoute.getLocationx());
			pstmt.setDouble(6, tripRoute.getLocationy());
			pstmt.setInt(7, tripRoute.getIndex());
			pstmt.setString(8, tripRoute.getMemo());
			pstmt.setInt(9, 0);

			int result = pstmt.executeUpdate();

			if (result >= 0) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // 데이터베이스 오류
	}

	// 여행 리스트 삭제하기

}
