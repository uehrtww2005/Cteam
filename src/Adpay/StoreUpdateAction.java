package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StoreDetail;
import bean.Tag;
import dao.StoreDetailDAO;
import dao.TagDAO;
import tool.Action;

public class StoreUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int storeId = Integer.parseInt(request.getParameter("store_id"));
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        String storeHours = startTime + " ～ " + endTime;

        String storeClose = request.getParameter("store_close");
        String storeIntroduct = request.getParameter("store_introduct");
        String seatDetail = request.getParameter("seat_detail");
        String selectedTagName = request.getParameter("selectedTag");

        StoreDetail detail = new StoreDetail();
        detail.setStoreId(storeId);
        detail.setStoreHours(storeHours);
        detail.setStoreClose(storeClose);
        detail.setStoreIntroduct(storeIntroduct);
        detail.setSeatDetail(seatDetail);

        if (selectedTagName != null && !selectedTagName.isEmpty()) {
            TagDAO tagDao = new TagDAO();
            Tag tag = tagDao.findByName(selectedTagName);
            detail.setSelectedTag(tag);
        }

        StoreDetailDAO dao = new StoreDetailDAO();
        // 初回登録か更新か判定
        if (!dao.exists(storeId)) {
            dao.save(detail);
        } else {
            dao.update(detail);
        }
        dao.saveSelectedTag(detail);

        response.sendRedirect(request.getContextPath() + "/Adpay/StoreDetail.action?store_id=" + storeId);
    }
}
