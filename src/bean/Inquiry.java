package bean;

import java.sql.Timestamp;

public class Inquiry {

    private Integer inquiryId;
    private String tel;
    private String content;
    private Integer userId;
    private Integer storeId;
    private Integer groupId;
    private Timestamp createdAt;   // ★追加
    private String userName;
    private String storeName;
    private String leaderName;


    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }

	// --- getter / setter ---
    public Integer getInquiryId() {
        return inquiryId;
    }
    public void setInquiryId(Integer inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getGroupId() {
        return groupId;
    }
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    // ★ createdAt の getter/setter 追加
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
