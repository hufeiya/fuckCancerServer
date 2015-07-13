package javaBean;

public class ArticleEntityBean {
	private int id;
	private String image;
	private String details;
	private String author;
	public int getId(){
		return this.id;
	}
	public String getImage(){
		return this.image;
	}
	public String getDetails(){
		return this.details;
	}
	public String getAuthor(){
		return this.author;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setImage(String image){
		this.image = image;
	}
	public void setDetails(String details){
		this.details = details;
	}
	public void setAuthor(String author){
		this.author = author;
	}
}
