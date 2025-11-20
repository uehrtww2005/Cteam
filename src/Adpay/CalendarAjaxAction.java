package Adpay;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StoreCalendar;
import dao.StoreCalendarDAO;
import tool.Action;

public class CalendarAjaxAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int storeId = Integer.parseInt(req.getParameter("store_id"));
        Date date = Date.valueOf(req.getParameter("date"));

        StoreCalendarDAO dao = new StoreCalendarDAO();
        for (StoreCalendar c : dao.getCalendarsByStoreId(storeId)) {
            if (c.getDate().equals(date)) {

                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().print(
                    "{ \"open\":\"" + c.getOpenTime() + "\", " +
                    "\"close\":\"" + c.getCloseTime() + "\" }"
                );
                return;
            }
        }

        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().print("{ \"open\":\"\", \"close\":\"\" }");
    }
}
