package share;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import plan.TripPlanRouteListModel;

public class ShareDAO {

	private Connection conn;
	private ResultSet rs;

	public ShareDAO() {
		try {
			String URL = "jdbc:mysql://localhost:3306/tripmate?serverTimezone=Asia/Seoul&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
			String id = "root";
			String password = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, id, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String makeCode() {
		String idx = "SHAR-";
		int num = getNext();
		if (num == -1)
			return "error";
		String code = idx + num;
		return code;
	}

	// �Խñ� ���� ����
	public int getNext() {

		String SQL = "SELECT COUNT(*) FROM tripShare ORDER BY trip_share_code DESC";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1;// ù ��° �Խù��� ���
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nERROR:�Խù� �ε��� ����\n");
		return 0;
	}

	// userId�� userCode ��������
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

		System.out.println("\nERROR:�Խù� �ε��� ����\n");

		return userCode;

	}

	// ������ �ð��� �������� �Լ�
	public String getDate() {
		String SQL = "SELECT NOW()";
		System.out.println("�ð�");
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "\n ERROR : ���� �ð��� ������ �� �����ϴ� \n"; // �����ͺ��̽� ����
	}

	// ���� �Խ��ǿ� �߰��ϱ�
	public String shareInfoInsert(ShareModel model) {

		String SQL = "INSERT INTO tripShare VALUES(?,?,?,?,?,?,?,?)";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, model.getShareCode());
			pstmt.setString(2, model.getUserCode());
			pstmt.setString(3, model.getPlanCode());
			pstmt.setString(4, model.getLocationTitle());
			pstmt.setString(5, model.getShareTitle());
			pstmt.setString(6, model.getContents());
			pstmt.setString(7, getDate());
			pstmt.setInt(8, 0);

			int result = pstmt.executeUpdate();

			if (result >= 0) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // �����ͺ��̽� ����
	}

	// �ۻ���
	public String delete(String plancode) {

		String SQL = "UPDATE tripShare SET delete_state = 1 WHERE plan_code = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, plancode);

			int result = pstmt.executeUpdate();

			if (result >= 0) {
				return "success";
			} else {
				return "error";
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return "error";
	}

	// ����Ʈ��������
	public ArrayList<ShareModel> getList() {

		String SQL = "SELECT plan_code,location_title,share_title,content FROM tripShare WHERE delete_state = 0";
		ArrayList<ShareModel> list = new ArrayList<ShareModel>();

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				ShareModel model = new ShareModel();

				model.setPlanCode(rs.getString(1));
				model.setLocationTitle(rs.getString(2));
				model.setShareTitle(rs.getString(3));
				model.setContents(rs.getString(4));

				list.add(model);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("error");

		return list;
	}

}
