package com.gamestore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * This class represents a Log record in the logs table in the database.
 * It's members are associated with the columns of a Log record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
public class Logs {

	@Id
	@GeneratedValue
	@Column(name="LogID")
	private int id;
	
	// Date only needs to be saved not the timestamp.
	@Temporal (TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy--mm-dd")
	@Column(name="Date")
	private Date date;
	
	@Column(name="UserId")
	private int userId;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="Transaction")
	private String transaction;
	
	@Column(name="GameName")
	private String gameName;
			
	@Column(name="Amount")
	private float amount;
		
	@Column(name="BalanceAfter")
	private float balanceAfter;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(float balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
