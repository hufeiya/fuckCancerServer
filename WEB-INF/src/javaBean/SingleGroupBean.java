package javaBean;

public class SingleGroupBean {
	private int num;
	private int sum;
	public SingleGroupBean(int sum,int num){
		this.sum = sum;
		this.num = num;
	}
	public SingleGroupBean(){
		
	}
	public void setNum(int num){
		this.num = num;
	}
	public void setSum(int sum ){
		this.sum = sum;
	}
	public int  getNum(){
		return this.num;
	}
	public int getSum(){
		return this.sum;
	}
}
