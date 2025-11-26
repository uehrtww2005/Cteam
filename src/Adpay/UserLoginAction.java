package Adpay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;   // group 用
import bean.Store;
import bean.User;
import dao.StoreDAO;
import dao.UserDAO;
import tool.Action;

public class UserLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        Group sessionGroup = (Group) session.getAttribute("group");

        StoreDAO storeDao = new StoreDAO();

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            // ▼ ログイン処理
            String address = request.getParameter("address");
            String password = request.getParameter("password");

            UserDAO dao = new UserDAO();
            User user = dao.login(address, password);

            if (user != null) {
                session.setAttribute("user", user);
                session.setAttribute("role", "user");
                request.setAttribute("user", user);

                setRankMessage(user, request);

                // ▼ 全店舗一覧を取得して request にセット
                List<Store> stores = storeDao.search(""); // 空文字で全件
                request.setAttribute("stores", stores);

                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています");
                request.getRequestDispatcher("/user/login_user.jsp").forward(request, response);
            }

        } else {
            // ▼ GET でアクセスされた場合（ホームリンクなど）
            if (sessionUser != null) {
                request.setAttribute("user", sessionUser);
                setRankMessage(sessionUser, request);

                // ▼ 全店舗一覧を取得して request にセット
                List<Store> stores = storeDao.search(""); // 空文字で全件
                request.setAttribute("stores", stores);

                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);

            } else if (sessionGroup != null) {
                request.setAttribute("group", sessionGroup);

                // ▼ group 用も同じく全店舗表示
                List<Store> stores = storeDao.search("");
                request.setAttribute("stores", stores);

                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);

            } else {
                // 未ログインならログインページへ
                response.sendRedirect(request.getContextPath() + "/Adpay/login.jsp");
            }
        }
    }

    // ▼ ランク判定とメッセージ設定
    private void setRankMessage(User user, HttpServletRequest request) throws Exception {
        UserDAO dao = new UserDAO();
        String oldRank = user.getRank();
        String newRank = judgeRank(user.getPrepaidAmount());

        String rankMessage;
        if (!oldRank.equals(newRank)) {
            user.setRank(newRank);
            dao.updateRank(user.getUserId(), newRank);
            rankMessage = "ランクアップおめでとうございます！ あなたは「" + newRank + "」になりました！";
        } else {
            int remaining = user.getRemainingToNextRank();
            if (remaining > 0) {
                rankMessage = "次のランクまで残り " + remaining + "円です";
            } else {
                rankMessage = "最高ランクです";
            }
        }

        // request/session にセット
        request.setAttribute("rankMsg", rankMessage);
        request.getSession().setAttribute("rankMsg", rankMessage);
    }

    // ▼ ランク判定
    private String judgeRank(int prepaidAmount) {
        if (prepaidAmount >= 40000) return "ゴールド";
        else if (prepaidAmount >= 15000) return "シルバー";
        else if (prepaidAmount >= 5000) return "ブロンズ";
        else return "ビギナー";
    }
}
