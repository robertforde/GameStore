package com.gamestore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * This class represents a Platform record in the platforms table in the database.
 * It's members are associated with the columns of an Platform record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates.
 * @author Robert Forde
 *
 */
@Entity
public class Platform {
	
	@Id
	@GeneratedValue
	@Column(name="PlatformID")
	private int id;
	
	@Column(name="Name")
	private String name;
	
	// Set up a One to Many relationship with Game
	@OneToMany
	// Joined by the PlatformId field in the Game table
	// Games not fetched automatically (FetchType Lazy)
	@JoinColumn(name="PlatformId")
	private List<Game> games;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
