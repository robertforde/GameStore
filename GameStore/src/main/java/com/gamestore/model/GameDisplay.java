package com.gamestore.model;

import java.util.List;

/**
 * This class represents a GameDisplay record which is used when a user clicks on a game to view it's details.
 * @author Robert Forde
 *
 */
public class GameDisplay {
	
	private Game game;
	
	private String largeCoverLoc;
	
	private String smallCoverLoc;
	
	private List<GameScreens> screens;

	
	public String getLargeCoverLoc() {
		return largeCoverLoc;
	}

	public void setLargeCoverLoc(String largeCoverLoc) {
		this.largeCoverLoc = largeCoverLoc;
	}

	public String getSmallCoverLoc() {
		return smallCoverLoc;
	}

	public void setSmallCoverLoc(String smallCoverLoc) {
		this.smallCoverLoc = smallCoverLoc;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<GameScreens> getScreens() {
		return screens;
	}

	public void setScreens(List<GameScreens> screens) {
		this.screens = screens;
	}
	
}
