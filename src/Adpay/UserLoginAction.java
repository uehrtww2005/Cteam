package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;

public class UserLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            // ▼ ログイン処理
            String address = request.getParameter("address");
            String password = request.getParameter("password");

            UserDAO dao = new UserDAO();
            User user = dao.login(address, password);

            if (user != null) {
                // ★追加: 利用停止チェック
                if (user.getStatus() == 1) {
                    request.setAttribute("msg", "このアカウントは現在利用停止されています。管理者にお問い合わせください。");
                    request.getRequestDispatcher("/user/login_user.jsp").forward(request, response);
                    return; // 処理をここで中断
                }

                // ログイン成功＆利用可能な場合のみセッション保存
                session.setAttribute("user", user);
                session.setAttribute("role", "user");
                request.setAttribute("user", user);

                setRankMessage(user, request);

                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています");
                request.getRequestDispatcher("/user/login_user.jsp").forward(request, response);
            }

        } else {
            // ▼ GETでアクセスされた場合（ホームリンクなど）
            if (sessionUser != null) {
                // セッションがある場合も、念のため再度DBでステータス確認をするのが安全ですが
                // ここでは簡易的にセッション維持のみとします
                request.setAttribute("user", sessionUser);
                setRankMessage(sessionUser, request);
                request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);
            } else {
                // 未ログインならトップページにリダイレクト
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
        request.setAttribute("rankMsg", rankMessage);
        HttpSession session = request.getSession();
        session.setAttribute("rankMsg", rankMessage);
    }

    // ▼ ランク判定
    private String judgeRank(int prepaidAmount) {
        if (prepaidAmount >= 40000) return "ゴールド";
        else if (prepaidAmount >= 15000) return "シルバー";
        else if (prepaidAmount >= 5000) return "ブロンズ";
        else return "ビギナー";
    }
}