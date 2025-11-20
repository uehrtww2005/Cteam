package Adpay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StoreCalendar; // カレンダー用Bean
import bean.StoreDetail;
import bean.Tag;
import dao.StoreDetailDAO;
import dao.TagDAO;
import tool.Action;

public class StoreDetailEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int storeId = 0;
        try {
            storeId = Integer.parseInt(req.getParameter("store_id"));
        } catch (Exception e) { }

        StoreDetailDAO dao = new StoreDetailDAO();
        StoreDetail detail = dao.getStoreDetailFullWithTags(storeId);

        // 新規登録時は detail を空で初期化
        if (detail == null) {
            detail = new StoreDetail();
            detail.setStoreId(storeId);
            detail.setCalendars(new ArrayList<StoreCalendar>()); // カレンダーリスト初期化
            detail.setSeats(new ArrayList<>());                  // 席リスト初期化
            // 文字列系フィールドは null のままで JSP で空表示される
        }

        // ▼ タグ一覧を取得
        TagDAO tagDao = new TagDAO();
        List<Tag> tagList = tagDao.findAll();
        req.setAttribute("allTags", tagList);

        // ▼ 今日から12ヶ月分のカレンダー情報を作成
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

        // ▼ カレンダーの時間を HH:mm 文字列に変換（空リストでも安全）
        for (StoreCalendar c : detail.getCalendars()) {
            String openStr = "";
            String closeStr = "";
            if (c.getOpenTime() != null) {
                openStr = c.getOpenTime().toString().substring(0,5);
            }
            if (c.getCloseTime() != null) {
                closeStr = c.getCloseTime().toString().substring(0,5);
            }
            c.setOpenTimeStr(openStr);
            c.setCloseTimeStr(closeStr);
        }

        // ▼ 店舗詳細データを JSP に渡す
        req.setAttribute("detail", detail);

        req.getRequestDispatcher("/shop/store_detail_edit.jsp").forward(req, res);
    }
}
