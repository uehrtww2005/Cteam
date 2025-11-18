package bean;

public class Inquiry {
    private int inquiryId;
    private String tel;
    private String content;
    private Integer userId;   // NULL許可
    private Integer storeId;  // NULL許可
    private Integer groupId;  // NULL許可

    // --- getter / setter ---
    public int getInquiryId() {
        return inquiryId;
    }
    public void setInquiryId(int inquiryId) {
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
}
