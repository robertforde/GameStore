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
 * This class represents a Views record in the views table in the database.
 * It's members are associated with the columns of a Views record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
@Table(name = "views")
public class Views2{

	@Id
	@GeneratedValue
	@Column(name="ViewID")
	private int id;
	
	// Set up a many to one relationship with Game
	@ManyToOne
	@Transient
	// Joined by the GameID field in the Game table
	// Game fetched automatically (FetchType Eager)
	@JoinColumn(name="GameID")
	private Game game;
		
	@Column(name="GameId")
	private int gameId;
	
	@Column(name="UserId")
	private int userId;
	
	// Date only needs to be saved not the timestamp.
	@Temporal (TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="ViewDate")
	private Date date;
	
	// This field is not part of the Views table
	@Transient
	private int viewTotal;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getViewTotal() {
		return viewTotal;
	}

	public void setViewTotal(int viewTotal) {
		this.viewTotal = viewTotal;
	}
	
}
