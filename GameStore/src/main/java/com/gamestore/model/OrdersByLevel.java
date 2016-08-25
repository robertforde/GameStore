package com.gamestore.model;

/**
 * This is a POJO class used to create the orders by User Level chart 
 * @author Robert Forde
 *
 */
public class OrdersByLevel {
	
	private long count;
	private String level;
	
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
