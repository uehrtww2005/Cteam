package Adpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class StoreHomeAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/shop/store_home.jsp")
               .forward(request, response);

        System.out.println("★★ StoreHomeAction 呼ばれた ★★");

    }
}

