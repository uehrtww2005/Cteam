package bean;

public class Group {

	private int groupId;
	private String leaderAddress;
	private String password;
	private String leaderName;
	private String leaderTel;
	private String rank;
	private int prepaidAmount;


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
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getLeaderAddress() {
		return leaderAddress;
	}
	public void setLeaderAddress(String leaderAddress) {
		this.leaderAddress = leaderAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getLeaderTel() {
		return leaderTel;
	}
	public void setLeaderTel(String leaderTel) {
		this.leaderTel = leaderTel;
	}
}
