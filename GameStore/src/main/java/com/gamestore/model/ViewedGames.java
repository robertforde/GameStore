package com.gamestore.model;

/**
 * This is a POJO class used to produce the most viewed games (last 30 days) by platforms 
 * @author Robert Forde
 *
 */
public class ViewedGames{

	private long count;
	private Game viewsGame;
	private int gId;
	private String location;
	
	
	public int getgId() {
		return gId;
	}
	
	public void setgId(int gId) {
		this.gId = gId;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
	public Game getViewsGame() {
		return viewsGame;
	}
	
	public void setViewsGame(Game viewsGame) {
		this.viewsGame = viewsGame;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
			
}
