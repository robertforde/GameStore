package com.gamestore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * This class represents a Game record in the game table in the database.
 * It's members are associated with the columns of a Game record and it's relationships with other tables are also represented for the 
 * benefit of data retrieval and updates. 
 * @author Robert Forde
 *
 */
@Entity
public class Game{
	
	@Id
	@GeneratedValue
	@Column(name="GameID")
	private int id;
	
	@Column(name="Name")
	private String name;

	// Set up a many to one relationship with Platform
	@ManyToOne
	// Joined by the PlatformID field in the Platform table
	// Platform fetched automatically (FetchType Eager)
	@JoinColumn(name="PlatformID")
	private Platform platform;
	
	@Column(name="PlatformId")
	private int platformId;
	
	// Set up a many to one relationship with Genre
	@ManyToOne
	// Joined by the GenreID field in the Genre table
	// Genre fetched automatically (FetchType Eager)
	@JoinColumn(name="GenreID")
	private Genre genre;
	
	@Column(name="GenreId")
	private int genreId;
	
	@Column(name="Publisher")
	private String publisher;
	
	@Column(name="Year")
	private int year;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="BronzePrice")
	private float bronzePrice;
	
	@Column(name="SilverPrice")
	private float silverPrice;
	
	@Column(name="GoldPrice")
	private float GoldPrice;
	
	@Column(name="Views")
	private int totalViews;
	
	@Column(name="Sales")
	private int totalSales;
	
	// Set up a One to Many relationship with Order
	@OneToMany
	// Joined by the GameId field in the Order table
	// Orders not fetched automatically (FetchType Lazy)
	@JoinColumn(name="GameId")
	private List<Orders> orders;

	// Set up a One to Many relationship with GameScreens
	@OneToMany
	// Joined by the GameId field in the GameScreens table
	// Game Images not fetched automatically (FetchType Lazy)
	@JoinColumn(name="GameId")
	private List<GameScreens> gameScreens;

	// Set up a One to Many relationship with View
	@OneToMany
	// Joined by the GameId field in the View table
	// Views not fetched automatically (FetchType Lazy)
	@JoinColumn(name="GameId")
	private List<Views> views;

	// Set up a One to Many relationship with GameCover table
	@OneToMany
	// Joined by the GameId field in the GameCover table
	// Images not fetched automatically (FetchType Lazy)
	@JoinColumn(name="GameId")
	private List<GameCover> covers;
		
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

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getBronzePrice() {
		return bronzePrice;
	}

	public void setBronzePrice(float bronzePrice) {
		this.bronzePrice = bronzePrice;
	}

	public float getSilverPrice() {
		return silverPrice;
	}

	public void setSilverPrice(float silverPrice) {
		this.silverPrice = silverPrice;
	}

	public float getGoldPrice() {
		return GoldPrice;
	}

	public void setGoldPrice(float goldPrice) {
		GoldPrice = goldPrice;
	}

	public int getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

	public int getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<Views> getViews() {
		return views;
	}

	public void setViews(List<Views> views) {
		this.views = views;
	}

	public List<GameScreens> getGameScreens() {
		return gameScreens;
	}

	public void setGameScreens(List<GameScreens> gameScreens) {
		this.gameScreens = gameScreens;
	}

	public List<GameCover> getCovers() {
		return covers;
	}

	public void setCovers(List<GameCover> covers) {
		this.covers = covers;
	}
	
}
