package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Group;
import dao.GroupDAO;
import tool.Action;

public class GroupLoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String leaderAddress = request.getParameter("leader_address");
        String password = request.getParameter("password");

        GroupDAO dao = new GroupDAO();
        Group group = dao.login(leaderAddress, password);

        if (group != null) {
            HttpSession session = request.getSession();
            session.setAttribute("group", group);
            request.setAttribute("group", group);

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
            request.getRequestDispatcher("/user/users_main.jsp").forward(request, response);

        } else {
            request.setAttribute("msg", "メールアドレスまたはパスワードが間違っています。");
            request.getRequestDispatcher("/user/group/login_group.jsp").forward(request, response);
        }
    }

    private String judgeRank(int prepaidAmount) {
        if (prepaidAmount >= 40000) return "ゴールド";
        else if (prepaidAmount >= 15000) return "シルバー";
        else if (prepaidAmount >= 5000) return "ブロンズ";
        else return "ビギナー";
    }
}
