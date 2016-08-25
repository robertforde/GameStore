package com.gamestore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * This class represents a Genre record in the genre table in the database.
 * It's members are associated with the columns of a Genre record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
public class Genre {
	
	@Id
	@Column(name="GenreID")
	private int id;
	
	@Column(name="Genre")
	private String genre;
	
	// Set up a One to Many relationship with Game
	@OneToMany
	// Joined by the GenreId field in the Game table
	// Games not fetched automatically (FetchType Lazy)
	@JoinColumn(name="GenreId")
	private List<Game> games;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
		
}
