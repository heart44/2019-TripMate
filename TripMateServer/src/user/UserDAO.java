package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.Board;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() throws ClassNotFoundException, SQLException {
		try {
			String URL = "jdbc:mysql://localhost:3306/triptest?serverTimezone=Asia/Seoul&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
			String id = "root";
			String password = "1234";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, id, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNext() {
		String SQL = "SELECT COUNT(*) as cnt FROM user";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int size = rs.getInt(1);
				return size + 1;
			}
			else 
				return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public String login(String userID, String userPassword) {
		String SQL = "SELECT user_pw From user WHERE user_id = ? AND delete_state = 0";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals(userPassword)) {
					return "success"; // 로그인 성공
				} else
					return "password-wrong"; // 로그인 실패
			}
			return "no-id"; // id가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}
	
	public String checkPassword(String userNickname, String userPassword) {
		String SQL = "SELECT user_pw From user WHERE user_nick = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userNickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals(userPassword)) {
					return "success"; // 비밀번호 맞음
				} else
					return "password-wrong"; // 비밀번호 틀림
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}

	public String register(User user) {
		String SQL = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?, 0)";
		try {
			String code = makeCode();
			if("error".equals(code)) {
				return "error" ;
			}
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, code);
			pstmt.setString(2, user.getUserID());
			pstmt.setString(3, user.getUserPassword());
			pstmt.setString(4, user.getUserNickname());
			pstmt.setInt(5, user.getUserAge());
			pstmt.setInt(6, user.getUserGender());
			/*
			 * pstmt.setString(7, user.getUserInterest1()); pstmt.setString(8,
			 * user.getUserInterest2()); pstmt.setString(9, user.getUserInterest3());
			 */
			pstmt.setString(7, user.getUserEmail());
			pstmt.setInt(8, 0);
			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success"; // insert문을 실행하면 무조건 0이상의 수가 반환됨

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "db-error"; // 데이터베이스 오류
	}
	
	public String update(String currentNickname,String nextNickname, String password) {
		String SQL = "UPDATE user SET user_nick = ?, user_pw = ? WHERE user_nick = ?";
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,nextNickname);
			pstmt.setString(2, password);
			pstmt.setString(3, currentNickname);

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
	
	public String delete(String nickname) {
		String SQL = "UPDATE user SET delete_state = 1 WHERE user_nick = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nickname);
			int result = pstmt.executeUpdate();
			if (result >= 0)
				return "success";
			else
				return "error";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String uidUpdate(String uid,String nickname) {
		String SQL = "UPDATE user SET user_uid = ? WHERE user_nick = ?";
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,uid);
			pstmt.setString(2,nickname);
			
			int result = pstmt.executeUpdate();
			if (result>=0) {
				return "success";
			}
			else {
				return "error";
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "error"; //데이터베이스 오류
	}
	
	
	public String checkIdDuplication(String id) {
		String SQL = "SELECT * From user";
		ArrayList<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getString(2));
				list.add(user);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUserID().equals(id)) {
					return "duplication";
				}
			}
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}

	public String checkNicknameDuplication(String nickname) {
		String SQL = "SELECT * From user";
		ArrayList<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserNickname(rs.getString(4));
				list.add(user);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUserNickname().equals(nickname)) {
					return "duplication";
				}
			}
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error"; // DB오류
	}

	public String emailSearch(String id) {
		String SQL = "SELECT user_email From user WHERE user_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String email = rs.getString(1);
				return email;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	public String idToNicknameSearch(String id) {
		String SQL = "SELECT user_nick From user WHERE user_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nickname = rs.getString(1);
				return nickname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String uidSearch(String nickname) {
		String SQL = "SELECT user_uid From user WHERE user_nick = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String uid = rs.getString(1);
				return uid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String uidToNickname(String uid) {
		String SQL = "SELECT user_nick From user WHERE user_uid = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nickname = rs.getString(1);
				return nickname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String codeSearch(String nickname) {
		String SQL = "SELECT user_code From user WHERE user_nick = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String code = rs.getString(1);
				return code;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	public String codeToNicknameSearch(String code) {
		String SQL = "SELECT user_nick From user WHERE user_code = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nickname = rs.getString(1);
				return nickname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	public String nicknameToUid(String nickname) {
		String SQL = "SELECT user_uid From user WHERE user_nick = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String uid = rs.getString(1);
				return uid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String nicknameToEmail(String nickname) {
		System.out.println(nickname);
		String SQL = "SELECT user_emal From user WHERE user_nick = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String email = rs.getString(1);
				return email;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public int usercodeToAge(String usercode) {
		String SQL = "SELECT user_age From user WHERE user_code = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, usercode);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int age = rs.getInt(1);
				return age;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int usercodeToGender(String usercode) {
		String SQL = "SELECT user_gender From user WHERE user_code = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, usercode);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int gender = rs.getInt(1);
				return gender;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public User getUser(String usercode) {
		String SQL = "SELECT * FROM user WHERE user_code = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,usercode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setUserNickname(rs.getString(4));
				user.setUserAge(rs.getInt(5));
				user.setUserGender(rs.getInt(6));
				return user;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String makeCode() {
		String idx = "USER-";
		int num = getNext();
		if(num == -1)
			return "error";
		String code = idx + num;
		return code;
	}

}
