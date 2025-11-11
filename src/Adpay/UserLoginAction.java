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
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.login(address, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            request.setAttribute("user", user);

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
            request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);

        } else {
            request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています");
            request.getRequestDispatcher("/user/login_user.jsp").forward(request, response);
        }
    }

    private String judgeRank(int prepaidAmount) {
        if (prepaidAmount >= 40000) return "ゴールド";
        else if (prepaidAmount >= 15000) return "シルバー";
        else if (prepaidAmount >= 5000) return "ブロンズ";
        else return "ビギナー";
    }
}
