package com.gamestore.model;


/**
 * This is a POJO class used to create the top 5 games (by platform/all platforms) chart 
 * @author Robert Forde
 *
 */
public class OrderedGames{
	
	private long count;
	private Game orderedGame;
	private int gId;
	
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Game getOrderedGame() {
		return orderedGame;
	}
	public void setOrderedGame(Game orderedGame) {
		this.orderedGame = orderedGame;
	}
	public int getgId() {
		return gId;
	}
	public void setgId(int gId) {
		this.gId = gId;
	}
	
	
}
