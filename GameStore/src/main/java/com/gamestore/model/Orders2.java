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


import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * This class represents an Order record in the orders table in the database.
 * It's members are associated with the columns of an Order record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
@Table(name = "orders")
public class Orders2 {

	@Id
	@GeneratedValue
	@Column(name="OrderID")
	private int id;
	
	// Set up a many to one relationship with User
	@Transient
	@ManyToOne
	// Joined by the UserID field in the User table
	// User fetched automatically (FetchType Eager)
	@JoinColumn(name="UserID")
	private User user;
		
	@Column(name="UserId")
	private int userId;
	
	// Set up a many to one relationship with Game
	@Transient
	@ManyToOne
	// Joined by the GameID field in the Game table
	// Game fetched automatically (FetchType Eager)
	@JoinColumn(name="GameID")
	private Game game;
		
	@Column(name="GameId")
	private int gameId;
	
	@Column(name="Status")
	private String status;
	
	// Date only needs to be saved not the timestamp.
	@Temporal (TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy--mm--dd")
	@Column(name="OrderDate")
	private Date date;
	
	@Column(name="OrderDay")
	private int orderDay;
	
	@Column(name="OrderMonth")
	private int orderMonth;
	
	@Column(name="OrderYear")
	private int orderYear;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrderDay() {
		return orderDay;
	}

	public void setOrderDay(int orderDay) {
		this.orderDay = orderDay;
	}

	public int getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(int orderMonth) {
		this.orderMonth = orderMonth;
	}

	public int getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(int orderYear) {
		this.orderYear = orderYear;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
