package bean;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class StoreCalendar {

    private int storeId;
    private Date date;
    private boolean isOpen;
    private Time openTime;
    private Time closeTime;

    // JSP表示用文字列
    private String openTimeStr;
    private String closeTimeStr;
    private String dateStr;

    // getter / setter
    public int getStoreId() { return storeId; }
    public void setStoreId(int storeId) { this.storeId = storeId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public boolean isOpen() { return isOpen; }
    public void setOpen(boolean isOpen) { this.isOpen = isOpen; }

    public Time getOpenTime() { return openTime; }
    public void setOpenTime(Time openTime) { this.openTime = openTime; }

    public Time getCloseTime() { return closeTime; }
    public void setCloseTime(Time closeTime) { this.closeTime = closeTime; }

    public String getOpenTimeStr() { return openTimeStr; }
    public void setOpenTimeStr(String openTimeStr) { this.openTimeStr = openTimeStr; }

    public String getCloseTimeStr() { return closeTimeStr; }
    public void setCloseTimeStr(String closeTimeStr) { this.closeTimeStr = closeTimeStr; }

    public String getDateStr() {
        if(dateStr != null) return dateStr;
        if(date != null) return new SimpleDateFormat("yyyy-MM-dd").format(date);
        return "";
    }
    public void setDateStr(String dateStr) { this.dateStr = dateStr; }
}
