package com.gamestore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * This class represents a User record in the user table in the database.
 * It's members are associated with the columns of a User record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
public class User {
	
	@Id
	@Column(name="UserID")
	private int id;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Approved")
	private String approved;
	
	@Column(name="Level")
	private String level;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="Forename")
	private String forename;
	
	@Column(name="Surname")
	private String surname;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="AccessLevel")
	private String accesslevel;
	
	@Column(name="HomePhone")
	private String homephone;
	
	@Column(name="Mobile")
	private String mobile;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="Balance")
	private float balance;
		
	// Set up a One to Many relationship with Order
	@OneToMany
	// Joined by the UserId field in the Order table
	// Orders not fetched automatically (FetchType Lazy)
	@JoinColumn(name="OrderId")
	private List<Orders> order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccesslevel() {
		return accesslevel;
	}

	public void setAccesslevel(String accesslevel) {
		this.accesslevel = accesslevel;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Orders> getOrder() {
		return order;
	}

	public void setOrder(List<Orders> order) {
		this.order = order;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}
