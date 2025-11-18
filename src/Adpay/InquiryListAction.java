package Adpay;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Inquiry;
import dao.DAO;
import dao.InquiryDAO;
import tool.Action;

public class InquiryListAction extends Action{

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // DB接続
        DAO baseDao = new DAO();
        Connection con = baseDao.getConnection();

        try {
            // DAO呼び出し
            InquiryDAO dao = new InquiryDAO();
            List<Inquiry> list = dao.findAll(con);

            // request にセット
            request.setAttribute("inquiryList", list);

        } finally {
            if (con != null) con.close();
        }

        // 管理者用ページにフォワード
        request.getRequestDispatcher("/admin/inquiry_list.jsp").forward(request, response);
    }
}
