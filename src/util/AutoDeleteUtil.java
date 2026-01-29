package util;

import dao.StoreCalendarDAO;

public class AutoDeleteUtil {

    public static void execute() {
        try {
            StoreCalendarDAO dao = new StoreCalendarDAO();
            dao.deleteAllPastCalendars();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

