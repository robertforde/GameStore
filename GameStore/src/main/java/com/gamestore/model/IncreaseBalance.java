package com.gamestore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * This class represents an Increase Balance record in the increasebalance table in the database.
 * It's members are associated with the columns of an IncreaseBalance record and it's relationships with other tables are also represented 
 * for the benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
@Table(name = "increasebalance")
public class IncreaseBalance{
	
	@Id
	@GeneratedValue
	@Column(name="IncreaseID")
	private int id;

	// Set up a many to one relationship with User
	@ManyToOne
	// Joined by the UserID field in the User table
	// Platform fetched automatically (FetchType Eager)
	@JoinColumn(name="UserID")
	private User user;
	
	@Column(name="UserId")
	private int userId;
	
	@Column(name="Increase")
	private float increase;
	
	@Column(name="Approved")
	private String approved;
	
	// Date only needs to be saved not the timestamp.
	@Temporal (TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="ApprovedDate")
	private Date date;

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getIncrease() {
		return increase;
	}

	public void setIncrease(float increase) {
		this.increase = increase;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
}
