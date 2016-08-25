package com.gamestore.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * This is a POJO class used to create the orders breakdown for last 30 days chart
 * @author Robert Forde
 *
 */
public class Orders30Days {
	
	private long count;
	
	@Temporal (TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date date;
	
	private String strDate;
	
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
		
}
