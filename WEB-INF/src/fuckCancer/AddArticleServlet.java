package fuckCancer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javaBean.GroupBean;
import javaBean.SingleGroupBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.ArticleDao;

public class AddArticleServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String tempArticle = req.getParameter("article");
		String [] temp  = tempArticle.split("\n");
		String title = req.getParameter("title");																																
		String image = req.getParameter("image");
		int columnx = Integer.parseInt(req.getParameter("columnx"));
		int type = Integer.parseInt(req.getParameter("type"));
		GroupBean gb = getGoupBean();
		if(gb == null){
			out.println("抱歉哦，内部错误，请后退重试一下");
			out.close();
			return;
		}
		SingleGroupBean sgb = new SingleGroupBean();
		sgb.setSum(gb.getSum()[type][columnx]);
		sgb.setNum(gb.getNum()[type][columnx]);
		if(sgb.getNum() >= 15){//when the numbers of each group are more than 15,the group number++.
			sgb.setNum(0);
			sgb.setSum(sgb.getSum() + 1);
			int num[][] = gb.getNum();
			num[type][columnx] = 0;
			gb.setNum(num);
			int sum[][] = gb.getSum();
			sum[type][columnx]++;
			gb.setSum(sum);
		}else{
			sgb.setNum(sgb.getNum() + 1);;
			int num[][] = gb.getNum();
			num[type][columnx]++;
			gb.setNum(num);
		}
		saveGroupBean(gb);
		out.println("<html><body>");
		ArticleDao dao = new ArticleDao();
		Boolean isInserted = dao.insertArticle(title, type, image, columnx,sgb.getSum(), tempArticle, "hufeiya");
		if(isInserted){
			out.println("插入成功！");
		}else{
			out.println("插入失败（⊙ｏ⊙）…");
		}
		//out.println(title+ type+image+columnx);
		out.println("</body></html>");
		out.close();
	}
	public int[] getCurrentMaxGroupNumberAndNumberOfThisGroup(int columnx,int type){
		 try{
			 	int a[] = new int[2];
			 	GroupBean gb = new GroupBean();
			 	ObjectInputStream is = new ObjectInputStream(new FileInputStream(  
	                    "~/info.txt")); 
			 	gb = (GroupBean)is.readObject();
			 	a[0] = gb.getSum()[type][columnx];
			 	a[1] = gb.getNum()[type][columnx];
			 	is.close();
			 	return a;
			 	
	        }catch(Exception ex){   
	            ex.printStackTrace();   
	            return null;
	        }   
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	public GroupBean getGoupBean(){
		GroupBean gb;
	 	
	 	ServletContext context = getServletContext();
	 	InputStream is = context.getResourceAsStream("/WEB-INF/info.txt");
	 	ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(is);
			gb = (GroupBean)ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
	 	
	 	return gb;
	}
	public void saveGroupBean(GroupBean gb){//a test to create the original info.txt
		    try{
		    	ServletContext context = getServletContext();
		    	File file = new File(context.getRealPath("/")
		                + "WEB-INF/info.txt");
		    	FileOutputStream fs = new FileOutputStream(file);
		      ObjectOutputStream os =  new ObjectOutputStream(fs);  
		      os.writeObject(gb);
		      os.close();
		    }catch (Exception e){
		      e.printStackTrace();
		    }
	}
}
