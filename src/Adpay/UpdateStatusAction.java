package Adpay; // パッケージ名は環境に合わせてください

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import tool.Action; // 独自のActionクラスを継承

public class UpdateStatusAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // パラメータの取得
        String type = request.getParameter("type"); // user, group, store
        // IDやStatusは数値変換する
        int id = Integer.parseInt(request.getParameter("id"));
        int currentStatus = Integer.parseInt(request.getParameter("currentStatus"));

        // 新しいステータスを決定 (0なら1へ、1なら0へ反転)
        int newStatus = (currentStatus == 0) ? 1 : 0;

        // DAOを使って更新
        AdminDAO dao = new AdminDAO();
        dao.updateStatus(type, id, newStatus);

        // 更新後、元のリスト画面を表示するためにフォワードまたはリダイレクト
        // ※データ再取得のため、JSPではなくActionへ飛ばします
        if ("store".equals(type)) {
            // 店舗一覧のアクションへ
            request.getRequestDispatcher("StoreListServlet.action").forward(request, response);
        } else {
            // 利用者・団体一覧のアクションへ
            request.getRequestDispatcher("UserListServlet.action").forward(request, response);
        }
    }
}