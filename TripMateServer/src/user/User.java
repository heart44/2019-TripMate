package user;


public class User {
	private String userCode;
	private String userID;
	private String userPassword;
	private String userNickname;
	private int userAge;
	private int userGender;
	private String userEmail;
	public User() {
		
	}
	
	
	public User(String userID, String userPassword, String userNickname, int userAge, int userGender, String userEmail) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userNickname = userNickname;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userEmail = userEmail;
	}
	
	
	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getUserGender() {
		return userGender;
	}
	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}