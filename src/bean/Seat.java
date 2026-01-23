package bean;

public class Seat {
    private int seatId;
    private int storeId;
    private String seatType;
    private String seatName;
    private int minPeople;
    private boolean isActive;

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }


    // コンストラクタ
    public Seat() {}

    // Getter / Setter
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(int minPeople) {
        this.minPeople = minPeople;
    }
}
