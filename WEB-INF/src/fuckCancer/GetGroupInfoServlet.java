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

import com.google.gson.Gson;

import Dao.ArticleDao;

public class GetGroupInfoServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		int columnx = Integer.parseInt(req.getParameter("columnx"));
		int type = Integer.parseInt(req.getParameter("type"));
		//fuckyou();//create the info.txt only in testing
		SingleGroupBean sgb = getCurrentMaxGroupNumberAndNumberOfThisGroup(columnx, type);
		final Gson gson = new Gson();
		final String json = gson.toJson(sgb);
		out.println(json);
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
	public void fuckyou(){//a test to create the original info.txt
		GroupBean gb = new GroupBean();
		    int num[][] = new int[20][20];
		    int sum[][] = new int[20][20];
		    for(int i =0; i < 20;i++){
		      for(int j = 0; j < 20;j++){
		        num[i][j] = 15;
		        sum[i][j]=8;
		      }
		    }
		    gb.setNum(num);
		    gb.setSum(sum);
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
