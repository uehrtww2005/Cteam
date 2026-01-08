package bean;

public class Coupon {

	private int couponId;
	private int storeId;  //storeIdは外部キー
	private String couponName;
	private String couponIntroduct;
	private String couponRank;


	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponIntroduct() {
		return couponIntroduct;
	}
	public void setCouponIntroduct(String couponIntroduct) {
		this.couponIntroduct = couponIntroduct;
	}
	public String getCouponRank() {
		return couponRank;
	}
	public void setCouponRank(String couponRank) {
		this.couponRank = couponRank;
	}


}
