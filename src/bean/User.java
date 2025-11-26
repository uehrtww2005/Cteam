package bean;

public class User {

	private int userId;
	private String address;
	private String password;
	private String userName;
	private int gender;
	private String userTel;
	private String rank;
	private int prepaidAmount;
	// 例: User.java などに以下を追加
	private int status;
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }


	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getPrepaidAmount() {
		return prepaidAmount;
	}
	public void setPrepaidAmount(int prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}

	// ★ 次のランクまでの残額を計算するメソッド ★
	public int getRemainingToNextRank() {
	    switch (rank) {
	        case "ビギナー":
	            return Math.max(5000 - prepaidAmount, 0);
	        case "ブロンズ":
	            return Math.max(15000 - prepaidAmount, 0);
	        case "シルバー":
	            return Math.max(40000 - prepaidAmount, 0);
	        case "ゴールド":
	            return 0; // 最高ランク
	        default:
	            return 0;
	    }
	}


}
