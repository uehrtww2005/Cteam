package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import bean.Store;
import dao.GroupDAO;
import dao.StoreDAO;
import tool.Action;

public class GroupLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Group sessionGroup = (Group) session.getAttribute("group");

        StoreDAO storeDao = new StoreDAO();

        // ====== POST（ログイン時） ======
        if ("POST".equalsIgnoreCase(request.getMethod())) {

            request.setCharacterEncoding("UTF-8");
            String leaderAddress = request.getParameter("leader_address");
            String password = request.getParameter("password");

            GroupDAO dao = new GroupDAO();
            Group group = dao.login(leaderAddress, password);

            if (group != null) {

            	if (group.getStatus() == 1){
            		request.setAttribute("msg", "この団体アカウントは現在利用停止されています。管理者にお問い合わせください。");
            		request.getRequestDispatcher("/user/group/login_group.jsp").forward(request, response);
            	    return;
            	}
                session.setAttribute("group", group);
                session.setAttribute("role", "group");
                request.setAttribute("group", group);

                setRankMessage(group, request);

                // ▼ 全店舗一覧を取得して request にセット
                List<Store> stores = storeDao.search(""); // 空文字で全件
                request.setAttribute("stores", stores);

                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています。");
                request.getRequestDispatcher("/user/group/login_group.jsp").forward(request, response);
            }

        } else {

            // ====== GET（ホーム押下など） ======
            if (sessionGroup != null) {
                request.setAttribute("group", sessionGroup);

                setRankMessage(sessionGroup, request);

                // ▼ 全店舗一覧を取得して request にセット
                List<Store> stores = storeDao.search("");
                request.setAttribute("stores", stores);

                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);

            } else {
                // 未ログインならログイン画面にリダイレクト
                response.sendRedirect(request.getContextPath() + "/user/group/login_group.jsp");
            }
        }
    }

    // ====== ランクメッセージ生成共通メソッド ======
    private void setRankMessage(Group group, HttpServletRequest request) throws Exception {
        GroupDAO dao = new GroupDAO();

        String oldRank = group.getRank();
        String newRank = judgeRank(group.getPrepaidAmount());

        String rankMessage;

        if (!oldRank.equals(newRank)) {
            group.setRank(newRank);
            dao.updateRank(group.getGroupId(), newRank);
            rankMessage = "ランクアップおめでとうございます！ あなたは「" + newRank + "」になりました！";
        } else {
            int remaining = group.getRemainingToNextRank();
            if (remaining > 0) {
                rankMessage = "次のランクまで残り " + remaining + "円です";
            } else {
                rankMessage = "最高ランクです";
            }
        }

        request.setAttribute("rankMsg", rankMessage);
        request.getSession().setAttribute("rankMsg", rankMessage);
    }

    // ====== ランク判定 ======
    private String judgeRank(int prepaidAmount) {
        if (prepaidAmount >= 40000) return "ゴールド";
        else if (prepaidAmount >= 15000) return "シルバー";
        else if (prepaidAmount >= 5000) return "ブロンズ";
        else return "ビギナー";
    }
}
