package bean;

import java.util.List;

public class Store {

    private int storeId;
    private String password;
    private String storeName;
    private String storeAddress;
    private String storeTel;

    private StoreDetail storeDetail; // 1店舗に1つの詳細
    private List<Menu> menu;  		 // 1店舗に複数のメニュー

	public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreTel() {
        return storeTel;
    }
    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    public StoreDetail getStoreDetail() {
        return storeDetail;
    }
    public void setStoreDetail(StoreDetail storeDetail) {
        this.storeDetail = storeDetail;
    }

    public List<Menu> getMenu() {
        return menu;
    }
    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
