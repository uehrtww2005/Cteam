package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserCoupon implements Serializable {

    private int userCouponId;

    private Integer userId;
    private Integer groupId;

    private int couponId;
    private int storeId;

    // ★ クーポン情報（DAOでJOINして取得）
    private String couponName;
    private String couponRank;
    private String couponIntroduct;

    private boolean used;
    private Timestamp receivedAt;
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /* getter / setter */

    public int getUserCouponId() {
        return userCouponId;
    }
    public void setUserCouponId(int userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

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

    public String getCouponRank() {
        return couponRank;
    }
    public void setCouponRank(String couponRank) {
        this.couponRank = couponRank;
    }

    public String getCouponIntroduct() {
        return couponIntroduct;
    }
    public void setCouponIntroduct(String couponIntroduct) {
        this.couponIntroduct = couponIntroduct;
    }

    public boolean isUsed() {
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }

    public Timestamp getReceivedAt() {
        return receivedAt;
    }
    public void setReceivedAt(Timestamp receivedAt) {
        this.receivedAt = receivedAt;
    }
}
