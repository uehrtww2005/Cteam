package Adpay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StoreCalendar;
import bean.StoreDetail;
import bean.Tag;
import dao.StoreCalendarDAO;
import dao.StoreDetailDAO;
import dao.TagDAO;
import tool.Action;

public class StoreDetailEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int storeId = 0;
        try {
            storeId = Integer.parseInt(req.getParameter("store_id"));
        } catch (Exception e) {}

        // ===============================
        // ★ 自動削除（ここが唯一の場所）
        // ===============================
        StoreCalendarDAO calDao = new StoreCalendarDAO();
        calDao.deletePastCalendarsByStoreId(storeId);

        // 店舗詳細取得
        StoreDetailDAO dao = new StoreDetailDAO();
        StoreDetail detail = dao.getStoreDetailFullWithTags(storeId);

        // 新規時
        if (detail == null) {
            detail = new StoreDetail();
            detail.setStoreId(storeId);
            detail.setCalendars(new ArrayList<>());
            detail.setSeats(new ArrayList<>());
        }

        // ===============================
        // ★ 店舗紹介文 ＜ ＞ 自動削除（ここを追加）
        // ===============================
        String intro = req.getParameter("storeIntroduct");
        if (intro != null) {
            intro = intro.replace("<", "").replace(">", "");
            detail.setStoreIntroduct(intro);
        }

        // タグ一覧
        TagDAO tagDao = new TagDAO();
        List<Tag> tagList = tagDao.findAll();
        req.setAttribute("allTags", tagList);

        // ===============================
        // カレンダー表示用（12ヶ月）
        // ===============================
        Calendar base = Calendar.getInstance();
        List<Integer> years = new ArrayList<>();
        List<Integer> months = new ArrayList<>();
        List<Integer> startDays = new ArrayList<>();
        List<Integer> lastDays = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            int year = base.get(Calendar.YEAR);
            int month = base.get(Calendar.MONTH) + 1;

            Calendar tmp = Calendar.getInstance();
            tmp.set(year, month - 1, 1);

            years.add(year);
            months.add(month);
            startDays.add(tmp.get(Calendar.DAY_OF_WEEK));
            lastDays.add(tmp.getActualMaximum(Calendar.DAY_OF_MONTH));

            base.add(Calendar.MONTH, 1);
        }

        req.setAttribute("years", years);
        req.setAttribute("months", months);
        req.setAttribute("startDays", startDays);
        req.setAttribute("lastDays", lastDays);

        // ===============================
        // 表示用データ整形
        // ===============================
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (StoreCalendar c : detail.getCalendars()) {

            if (c.getDate() != null) {
                c.setDateStr(df.format(c.getDate()));
            }

            String openStr = "";
            String closeStr = "";

            if (c.getOpenTime() != null) {
                openStr = c.getOpenTime().toString().substring(0, 5);
            }
            if (c.getCloseTime() != null) {
                closeStr = c.getCloseTime().toString().substring(0, 5);
            }

            c.setOpenTimeStr(openStr);
            c.setCloseTimeStr(closeStr);
        }

        req.setAttribute("detail", detail);
        req.getRequestDispatcher("/shop/store_detail_edit.jsp")
           .forward(req, res);
    }
}
