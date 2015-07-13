package javaBean;

import java.io.Serializable;

public class GroupBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8669257293287386585L;
	private int [][] num;
	private int [][] sum;
	public GroupBean(){
		
	}
	public void setNum(int [] []num){
		this.num = num;
	}
	public void setSum(int [][]sum ){
		this.sum = sum;
	}
	public int [][] getNum(){
		return this.num;
	}
	public int [][] getSum(){
		return this.sum;
	}
}
