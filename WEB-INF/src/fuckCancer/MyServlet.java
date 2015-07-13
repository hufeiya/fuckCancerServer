package fuckCancer;
import java.io.*;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.*;

import Dao.ArticleDao;
import javaBean.ArticleBean;
import javaBean.GroupBean;
import javaBean.SingleGroupBean;

import com.google.gson.*;
public class MyServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		//req.setCharacterEncoding("UTF-8"); 
        res.setContentType("text/html;charset=UTF-8"); 
        res.setCharacterEncoding("UTF-8"); 
        PrintWriter out = res.getWriter();
        int type =Integer.parseInt( req.getParameter("type"));
        int id = Integer.parseInt(req.getParameter("id"));
        int columnx = Integer.parseInt(req.getParameter("columnx"));
        int groups = Integer.parseInt(req.getParameter("groups"));
        if(groups == -88){//It's the first page the app  need.
        	SingleGroupBean sgb = getCurrentMaxGroupNumberAndNumberOfThisGroup(columnx, type);
        	groups = sgb.getSum();
        }else if(groups < 0){
        	return;
        }
        
        ArticleDao articleDao = new ArticleDao();
        List<ArticleBean> articles = articleDao.getArticlesFromId(id,type,columnx,groups);
        final Gson gson = new Gson();
        final String json = gson.toJson(articles);
        out.print(json);
        out.close();
    }
	public SingleGroupBean getCurrentMaxGroupNumberAndNumberOfThisGroup(int columnx,int type){
		 try{
			 	GroupBean gb;
			 	
			 	ServletContext context = getServletContext();
			 	InputStream is = context.getResourceAsStream("/WEB-INF/info.txt");
			 	ObjectInputStream ois = new ObjectInputStream(is); 
			 	gb = (GroupBean)ois.readObject();
			 	SingleGroupBean sgb = new SingleGroupBean(gb.getSum()[type][columnx],gb.getNum()[type][columnx]);
			 	ois.close();
			 	return sgb;
			 	
	        }catch(Exception ex){   
	            ex.printStackTrace();   
	            return null;
	        }   
	}
}