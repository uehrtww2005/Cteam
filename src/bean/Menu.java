package bean;

public class Menu {

    private int menuId;
    private int storeId;
    private String menuName;
    private int price;
    private String imageExtension;
    private String info;   // ★追加

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

    // ★詳細
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    public String getImagePath() {
        if (imageExtension == null || imageExtension.isEmpty()) {
            return "";
        }
        return "shop/store_menu_images/" + storeId + "_" + menuId + "." + imageExtension;
    }
}
