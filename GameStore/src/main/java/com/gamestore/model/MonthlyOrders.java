package com.gamestore.model;


/**
 * This is a POJO class used to create the orders by month chart 
 * @author Robert Forde
 *
 */
public class MonthlyOrders {

	private long count;
	private int month;
	
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
}
