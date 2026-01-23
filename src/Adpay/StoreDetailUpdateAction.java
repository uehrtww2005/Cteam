package Adpay;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
                    s.setMinPeople(
                        minPeopleArr[i] != null && !minPeopleArr[i].isEmpty()
                            ? Integer.parseInt(minPeopleArr[i])
                            : 0
                    );
                    seatList.add(s);
                }
            }
        }

        // ---------------- カレンダー情報 ----------------
        String[] dates = req.getParameterValues("date[]");
        String[] openTimes = req.getParameterValues("openTime[]");
        String[] closeTimes = req.getParameterValues("closeTime[]");
        String[] isClosedArr = req.getParameterValues("isClosed[]");

        System.out.println("---- カレンダー受信 ----");
        System.out.println("date[]      = " + Arrays.toString(dates));
        System.out.println("openTime[]  = " + Arrays.toString(openTimes));
        System.out.println("closeTime[] = " + Arrays.toString(closeTimes));
        System.out.println("isClosed[]  = " + Arrays.toString(isClosedArr));

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
                    if (openTimes[i] != null && !openTimes[i].isEmpty()) {
                        c.setOpenTime(Time.valueOf(openTimes[i] + ":00"));
                    }
                    if (closeTimes[i] != null && !closeTimes[i].isEmpty()) {
                        c.setCloseTime(Time.valueOf(closeTimes[i] + ":00"));
                    }
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

        // ---------------- カレンダー保存（★修正ポイント） ----------------
        StoreCalendarDAO calDao = new StoreCalendarDAO();

        // ① 店舗のカレンダーを全削除
        calDao.deleteCalendarsByStoreId(storeId);

        // ② 今日以降のみ登録
        LocalDate today = LocalDate.now();

        for (StoreCalendar c : calList) {
            LocalDate targetDate = c.getDate().toLocalDate();

            if (targetDate.isBefore(today)) {
                // 過去日は保存しない
                continue;
            }

            calDao.insertCalendar(storeId, c);
        }

        // ---------------- クーポン追加 ----------------
        String newName = req.getParameter("new_coupon_name");
        String newRank = req.getParameter("new_coupon_rank");
        String newIntro = req.getParameter("new_coupon_introduct");

        if (newRank != null && !newRank.isEmpty()
                && newIntro != null && !newIntro.isEmpty()
                && newName != null && !newName.isEmpty()) {

            Coupon coupon = new Coupon();
            coupon.setStoreId(storeId);
            coupon.setCouponRank(newRank);
            coupon.setCouponIntroduct(newIntro);
            coupon.setCouponName(newName);

            CouponDAO dao = new CouponDAO();
            dao.insert(coupon, storeId);

            System.out.println("クーポン追加完了: " + newRank);
        }

        // 編集画面にリダイレクト
        res.sendRedirect("StoreDetailEdit.action?store_id=" + storeId);
    }
}
