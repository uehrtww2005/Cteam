package bean;

public class Menu {

	private int menuId;
	private int storeId;  //storeIdは外部キー
	private String menuName;
	private int price;
	private String imageExtension;


	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImageExtension() {
		return imageExtension;
		}
	public void setImageExtension(String imageExtension) {
		this.imageExtension = imageExtension;
	}

	public String getImagePath() {
        if (imageExtension == null || imageExtension.isEmpty()) {
            return ""; // 画像がない場合
        }
        // Web上でアクセスできるパス
        return "shop/store_menu_images/" + storeId + "_" + menuId + "." + imageExtension;
    }

}
