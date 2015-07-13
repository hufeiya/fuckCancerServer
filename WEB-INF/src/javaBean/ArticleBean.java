package javaBean;

public class ArticleBean {
	private int id;
	private String title;
	private int type;
	private String image;
	private int columnx;
	private int groups;
	public int getId(){
		return this.id;
	}
	public String getTitle(){
		return this.title;
	}
	public int getType(){
		return this.type;
	}
	public String getImage(){
		return this.image;
	}
	public int getColumnx(){
		return this.columnx;
	}
	public int getGroups(){
		return this.groups;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setType(int type){
		this.type = type;
	}
	public void setImage(String image){
		this.image = image;
	}
	public void setColumnx(int columnx){
		this.columnx = columnx;
	}
	public void setGroups(int groups){
	this.groups = groups;
	}
}
