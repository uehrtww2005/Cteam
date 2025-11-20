package bean;

import java.sql.Date;
import java.sql.Time;

/**
 * StoreCalendar Bean
 *
 * store_calendar テーブルに対応するクラス。
 * 日付ごとの営業日・営業時間情報を保持。
 */
public class StoreCalendar {

    private int storeId;      // 店舗ID
    private Date date;        // 日付
    private boolean isOpen;   // 営業日かどうか
    private Time openTime;    // 開店時間
    private Time closeTime;   // 閉店時間

    // 追加：HH:mm形式の文字列（JSP表示用）
    private String openTimeStr;
    private String closeTimeStr;

    // getter / setter
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Time getOpenTime() {
        return openTime;
    }
    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenTimeStr() {
        return openTimeStr;
    }
    public void setOpenTimeStr(String openTimeStr) {
        this.openTimeStr = openTimeStr;
    }

    public String getCloseTimeStr() {
        return closeTimeStr;
    }
    public void setCloseTimeStr(String closeTimeStr) {
        this.closeTimeStr = closeTimeStr;
    }
}
