package com.gamestore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents a Game Cover record in the gamecover table in the database.
 * It's members are associated with the columns of a Gamecover record for the benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
public class GameCover {

	@Id
	@Column(name="GameCoverID")
	private int id;

	@Column(name="GameId")
	private int gameId;
	
	@Column(name="Location")
	private String location;
	
	@Column(name="Type")
	private String type;
	
	@Column(name="Width")
	private int width;
	
	@Column(name="Height")
	private int height;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
