package com.springboot.springboot.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author liushuang
 * @create 2019-08-01 13:31
 */
@WebServlet(urlPatterns = "/servlet",
        asyncSupported=true)
public class MyServlet extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取异步上下文
        AsyncContext asyncContext = req.startAsync();
        // 开启异步
        asyncContext.start(()-> {
            try {
                resp.getWriter().write("invoke Async");
                // 手动调用完成
                asyncContext.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        resp.getWriter().write("before Async ");
    }
}
