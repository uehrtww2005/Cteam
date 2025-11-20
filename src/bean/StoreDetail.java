package bean;

import java.util.List;

public class StoreDetail {

    private int detailId;
    private int storeId;
    private String storeIntroduct;

    private List<Seat> seats;                  // 店舗の席情報
    private List<StoreCalendar> calendars;     // 営業日カレンダー
    private String tag;                        // ← 1つだけ

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

    public String getStoreIntroduct() {
        return storeIntroduct;
    }

    public void setStoreIntroduct(String storeIntroduct) {
        this.storeIntroduct = storeIntroduct;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<StoreCalendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<StoreCalendar> calendars) {
        this.calendars = calendars;
    }

    // ▼ タグ（単一）
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
