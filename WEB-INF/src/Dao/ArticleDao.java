package Dao;
import com.google.gson.Gson; 

import javaBean.ArticleBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ArticleDao {
	private Connection dbConnection;
	private static final String GET_ARTICLE_BY_ID = "SELECT * FROM article WHERE ID=?";
	private static final String GET_ARTICLE_FROM_ID = "SELECT * FROM article WHERE ID>? AND "
			+ "type=? AND columnx=? AND groups=? ";
	private static final String GET_ARTICLE_FROM_ID_WITHOUT_GROUP = "SELECT * FROM article WHERE ID>? AND "
			+ "type=? AND columnx=?  ";
	private static final String INSERT_ARTICLE = " INSERT INTO article (title,type,image,columnx,groups) VALUES(?,?,?,?,?)";
	private static final String INSERT_ARTICLE_ENTITY =" INSERT INTO articleEntity (details,author) VALUES (?,?)";

	public ArticleDao(){
	
	}
	private void init(){
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/FUCK_CANCER?useUnicode=true&characterEncoding=utf8";
		String username = "root";
		String password = "4242";
		try {
			Class.forName(driver);
			dbConnection = DriverManager.getConnection(dburl, username, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean insertArticle(String title,int type,String image,int columnx,int groups,String details,String author){
		init();
		try {
			dbConnection.setAutoCommit(false);
			PreparedStatement pstmt = dbConnection.prepareStatement(INSERT_ARTICLE);
			PreparedStatement pstmt2 = dbConnection.prepareStatement(INSERT_ARTICLE_ENTITY);
			pstmt.setString(1, title);
			pstmt.setInt(2, type);
			pstmt.setString(3, image);
			pstmt.setInt(4, columnx);
			pstmt.setInt(5, groups);
			pstmt2.setString(1, details);
			pstmt2.setString(2, author);
			pstmt.execute();
			pstmt2.execute();
			dbConnection.commit();
			return true;
		} catch (Exception e) {
			try {
				dbConnection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
	}
	public ArticleBean getArticleById(int id){
		init();
		ArticleBean article = new ArticleBean();
		try {
			PreparedStatement pstmt = dbConnection.prepareStatement(GET_ARTICLE_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()){
				article.setId(id);
				article.setTitle(rst.getString("title"));
				article.setType(rst.getInt("type"));
				article.setColumnx(rst.getInt("columnx"));
				article.setImage(rst.getString("image"));
			}else{}
			return article;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<ArticleBean> getArticlesFromId(int id,int type,int columnx,int groups){
		init();
		try {
			List<ArticleBean> beans = new ArrayList<ArticleBean>();
			PreparedStatement pstmt = dbConnection.prepareStatement(GET_ARTICLE_FROM_ID);
			pstmt.setInt(1, id);
			pstmt.setInt(2, type);
			pstmt.setInt(3, columnx);
			pstmt.setInt(4, groups);
			ResultSet rst = pstmt.executeQuery();
			while(rst.next()){
				ArticleBean article = new ArticleBean();
				article.setId(rst.getInt("id"));
				article.setTitle(rst.getString("title"));
				article.setType(type);
				article.setColumnx(columnx);
				article.setImage(rst.getString("image"));
				article.setGroups(groups);
				beans.add(article);
			}
			//if this group contain too few element,add the former group
			if (beans.size() < 10 && (groups > 0)){
				List<ArticleBean> tempBeans = new ArrayList<ArticleBean>();
				pstmt.setInt(4, groups-1);
				ResultSet rst2 = pstmt.executeQuery();
				while(rst2.next()){
					ArticleBean article = new ArticleBean();
					article.setId(rst2.getInt("id"));
					article.setTitle(rst2.getString("title"));
					article.setType(type);
					article.setColumnx(columnx);
					article.setImage(rst2.getString("image"));
					article.setGroups(groups-1);
					tempBeans.add(article);
				}
				beans.addAll(0, tempBeans);
			}
			return beans;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public List<ArticleBean> getArticlesFromIdWithoutGroups(int id,int type,int columnx){
		init();
		try {
			List<ArticleBean> beans = new ArrayList<ArticleBean>();
			PreparedStatement pstmt = dbConnection.prepareStatement(GET_ARTICLE_FROM_ID_WITHOUT_GROUP);
			pstmt.setInt(1, id);
			pstmt.setInt(2, type);
			pstmt.setInt(3, columnx);
			ResultSet rst = pstmt.executeQuery();
			while(rst.next()){
				ArticleBean article = new ArticleBean();
				article.setId(rst.getInt("id"));
				article.setTitle(rst.getString("title"));
				article.setType(type);
				article.setColumnx(columnx);
				article.setImage(rst.getString("image"));
				beans.add(article);
			}

			return beans;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
