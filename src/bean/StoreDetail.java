package bean;

public class StoreDetail {

	private int detailId;
	private int storeId;  //storeIdは外部キー
	private String storeHours;
	private String storeClose;
	private String storeIntroduct;
	private String seatDetail;


	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreHours() {
		return storeHours;
	}
	public void setStoreHours(String storeHours) {
		this.storeHours = storeHours;
	}
	public String getStoreClose() {
		return storeClose;
	}
	public void setStoreClose(String storeClose) {
		this.storeClose = storeClose;
	}
	public String getStoreIntroduct() {
		return storeIntroduct;
	}
	public void setStoreIntroduct(String storeIntroduct) {
		this.storeIntroduct = storeIntroduct;
	}
	public String getSeatDetail() {
		return seatDetail;
	}
	public void setSeatDetail(String seatDetail) {
		this.seatDetail = seatDetail;
	}

}
