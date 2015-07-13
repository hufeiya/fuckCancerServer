package fuckCancer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javaBean.ArticleBean;
import javaBean.ArticleEntityBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.ArticleEntityDao;

import com.google.gson.Gson;

public class ArticleEntityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8"); 
        res.setContentType("text/html;charset=UTF-8"); 
        res.setCharacterEncoding("UTF-8"); 
        PrintWriter out = res.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        ArticleEntityDao aed = new ArticleEntityDao();
        ArticleEntityBean aeb = aed.selectEntityById(id);
        final Gson gson = new Gson();
        final String json = gson.toJson(aeb);
        out.print(json);
        out.close();
    }
}
