package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javaBean.ArticleEntityBean;

public class ArticleEntityDao {
	private Connection dbConnection;
	private static final String SELECT_ENTITY_BY_ID = "SELECT * FROM articleEntity WHERE  id=?";
	private void init(){
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/FUCK_CANCER?useUnicode=true&characterEncoding=utf-8";
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
	
	public ArticleEntityBean selectEntityById(int id){
		init();
		ArticleEntityBean articleEntity = new ArticleEntityBean();
		try {
			PreparedStatement pstmt = dbConnection.prepareStatement(SELECT_ENTITY_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()){
				articleEntity.setId(id);
				articleEntity.setDetails(rst.getString("details"));
				articleEntity.setAuthor(rst.getString("author"));
				articleEntity.setImage(rst.getString("image"));
				return articleEntity;
			}else{
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
