package Adpay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StoreDetail;
import dao.StoreDetailDAO;
import dao.TagDAO;
import tool.Action;

public class StoreDetailAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int storeId = Integer.parseInt(request.getParameter("store_id"));

        StoreDetailDAO dao = new StoreDetailDAO();
        StoreDetail detail = dao.getStoreDetailFullWithTags(storeId);

        // 全タグ取得
        TagDAO tagDao = new TagDAO();
        List<String> allTags = tagDao.findAll()
                                     .stream()
                                     .map(t -> t.getTagName())
                                     .collect(Collectors.toList());

        request.setAttribute("allTags", allTags);
        request.setAttribute("detail", detail);

        // 時間リスト（30分刻み）
        List<String> times = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 30) {
                String hh = String.format("%02d", h);
                String mm = String.format("%02d", m);
                times.add(hh + ":" + mm);
            }
        }
        request.setAttribute("times", times);

        // 初期値なしの場合は初期入力ページへ
        if (detail == null) {
            request.setAttribute("store_id", storeId);
            request.getRequestDispatcher("/shop/store_detail_init.jsp").forward(request, response);
            return;
        }

        // 編集ページへ
        request.getRequestDispatcher("/shop/store_detail.jsp").forward(request, response);
    }
}
