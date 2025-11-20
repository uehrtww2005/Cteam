package Adpay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bean.Inquiry;
import tool.Action;

public class UserInquiryAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            // GETアクセス：フォーム表示
            if (req.getMethod().equalsIgnoreCase("GET")) {
                req.getRequestDispatcher("/user/inquiry.jsp").forward(req, res);
                return;
            }

            req.setCharacterEncoding("UTF-8");

            // パラメータ取得
            String tel = req.getParameter("tel");
            String content = req.getParameter("content");
            String groupIdStr = req.getParameter("group_id");

            Integer userId = null;
            Integer storeId = null;
            Integer groupId = null;

            Object user = req.getSession().getAttribute("user");
            Object store = req.getSession().getAttribute("store");

            if (user != null) userId = ((bean.User) user).getUserId();
            if (store != null) storeId = ((bean.Store) store).getStoreId();
            if (groupIdStr != null && !groupIdStr.trim().isEmpty()) {
                try {
                    groupId = Integer.parseInt(groupIdStr);
                } catch (NumberFormatException e) {
                    forwardWithMessage(req, res, "group_idは数値で入力してください。", userId, storeId, null);
                    return;
                }
            }

            // 入力チェック
            if ((tel == null || tel.trim().isEmpty()) && (content == null || content.trim().isEmpty())) {
                forwardWithMessage(req, res, "電話番号か内容を入力してください。", userId, storeId, groupId);
                return;
            }

            Inquiry inquiry = new Inquiry();
            inquiry.setTel(tel);
            inquiry.setContent(content);
            inquiry.setUserId(userId);
            inquiry.setStoreId(storeId);
            inquiry.setGroupId(groupId);

            // --- JNDIからDataSource取得 ---
            Context initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/adpay");

            try (Connection con = ds.getConnection()) {
                String sql = "INSERT INTO inquiry(tel, content, user_id, store_id, group_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement st = con.prepareStatement(sql)) {

                    st.setString(1, inquiry.getTel());
                    st.setString(2, inquiry.getContent());

                    if (inquiry.getUserId() != null) st.setInt(3, inquiry.getUserId());
                    else st.setNull(3, java.sql.Types.INTEGER);

                    if (inquiry.getStoreId() != null) st.setInt(4, inquiry.getStoreId());
                    else st.setNull(4, java.sql.Types.INTEGER);

                    if (inquiry.getGroupId() != null) st.setInt(5, inquiry.getGroupId());
                    else st.setNull(5, java.sql.Types.INTEGER);

                    st.executeUpdate();
                }
            }

            req.setAttribute("msg", "お問い合わせを受け付けました。ありがとうございました。");
            req.getRequestDispatcher("/user/inquiry.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            forwardWithMessage(req, res, "お問い合わせ処理中にエラーが発生しました。", null, null, null);
        }
    }

    private void forwardWithMessage(HttpServletRequest req, HttpServletResponse res, String msg,
                                    Integer userId, Integer storeId, Integer groupId) throws ServletException, IOException {
        req.setAttribute("msg", msg);
        req.setAttribute("user_id", userId);
        req.setAttribute("store_id", storeId);
        req.setAttribute("group_id", groupId);
        req.getRequestDispatcher("/user/inquiry.jsp").forward(req, res);
    }
}
