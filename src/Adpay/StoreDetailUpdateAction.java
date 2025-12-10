package Adpay;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Coupon;
import bean.Seat;
import bean.StoreCalendar;
import bean.StoreDetail;
import dao.CouponDAO;
import dao.SeatDAO;
import dao.StoreCalendarDAO;
import dao.StoreDetailDAO;
import tool.Action;

public class StoreDetailUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int storeId = Integer.parseInt(req.getParameter("store_id"));
        String intro = req.getParameter("storeIntroduct");
        String tag = req.getParameter("tag");


        // ---------------- 席情報 ----------------
        String[] seatNames = req.getParameterValues("seatName");
        String[] seatTypes = req.getParameterValues("seatType");
        String[] minPeopleArr = req.getParameterValues("minPeople");

        List<Seat> seatList = new ArrayList<>();
        if (seatNames != null) {
            for (int i = 0; i < seatNames.length; i++) {
                if (seatNames[i] != null && !seatNames[i].trim().isEmpty()) {
                    Seat s = new Seat();
                    s.setStoreId(storeId);
                    s.setSeatName(seatNames[i]);
                    s.setSeatType(seatTypes[i]);
                    s.setMinPeople(minPeopleArr[i] != null && !minPeopleArr[i].isEmpty() ?
                            Integer.parseInt(minPeopleArr[i]) : 0);
                    seatList.add(s);
                }
            }
        }

     // ---------------- カレンダー情報 ----------------
        String[] dates = req.getParameterValues("date[]");
        String[] openTimes = req.getParameterValues("openTime[]");
        String[] closeTimes = req.getParameterValues("closeTime[]");
        String[] isClosedArr = req.getParameterValues("isClosed[]");

        System.out.println("---- カレンダー受信テスト ----");
        System.out.println("---- calendar test ----");
        System.out.println("date[] = " + Arrays.toString(req.getParameterValues("date[]")));
        System.out.println("openTime[] = " + Arrays.toString(req.getParameterValues("openTime[]")));
        System.out.println("closeTime[] = " + Arrays.toString(req.getParameterValues("closeTime[]")));
        System.out.println("isClosed[] = " + Arrays.toString(req.getParameterValues("isClosed[]")));


        List<StoreCalendar> calList = new ArrayList<>();
        if (dates != null) {
            for (int i = 0; i < dates.length; i++) {
                if (dates[i] == null || dates[i].isEmpty()) continue;

                StoreCalendar c = new StoreCalendar();
                c.setStoreId(storeId);
                c.setDate(Date.valueOf(dates[i]));

                boolean isClosed = "true".equals(isClosedArr[i]);
                c.setOpen(!isClosed);

                if (!isClosed) {
                    if (openTimes[i] != null && !openTimes[i].isEmpty())
                        c.setOpenTime(Time.valueOf(openTimes[i] + ":00"));
                    if (closeTimes[i] != null && !closeTimes[i].isEmpty())
                        c.setCloseTime(Time.valueOf(closeTimes[i] + ":00"));
                } else {
                    c.setOpenTime(null);
                    c.setCloseTime(null);
                }

                calList.add(c);
            }
        }


        // ---------------- 店舗詳細保存 ----------------
        StoreDetail detail = new StoreDetail();
        detail.setStoreId(storeId);
        detail.setStoreIntroduct(intro);
        detail.setTag(tag);

        StoreDetailDAO detailDao = new StoreDetailDAO();
        detailDao.saveOrUpdateStoreDetail(detail);

        // ---------------- 席情報保存 ----------------
        SeatDAO seatDao = new SeatDAO();
        seatDao.deleteSeatsByStoreId(storeId);
        for (Seat s : seatList) {
            seatDao.insertSeat(s);
        }

        // ---------------- カレンダー保存 ----------------
        StoreCalendarDAO calDao = new StoreCalendarDAO();
        calDao.deleteCalendarsByStoreId(storeId);
        for (StoreCalendar c : calList) {
            calDao.insertCalendar(storeId, c);
        }

        StoreCalendarDAO calDAO = new StoreCalendarDAO();
        calDAO.deletePastCalendars(); // 今日より前のデータを自動削除

        String newRank = req.getParameter("new_coupon_rank");
        String newIntro = req.getParameter("new_coupon_introduct");

        if (newRank != null && !newRank.isEmpty() &&
            newIntro != null && !newIntro.isEmpty()) {

            Coupon coupon = new Coupon();
            coupon.setStoreId(storeId);
            coupon.setCouponRank(newRank);
            coupon.setCouponIntroduct(newIntro);

            CouponDAO dao = new CouponDAO();
            dao.insert(coupon, storeId);

            System.out.println("クーポン追加完了: " + newRank);
        }

        // 編集画面にリダイレクト
        res.sendRedirect("StoreDetailEdit.action?store_id=" + storeId);
    }
}
