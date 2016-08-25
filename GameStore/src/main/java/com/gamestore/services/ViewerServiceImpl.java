package com.gamestore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamestore.dao.AdminDAO;
import com.gamestore.dao.ViewerDAO;
import com.gamestore.model.Game;
import com.gamestore.model.Platform;
import com.gamestore.model.ViewedGames;


/**
 * This is the service class which is autowired with dao interfaces to allow it to call dao methods. It is the class that controllers can 
 * use to retrieve/save any viewer type functionality. 
 * @author Robert Forde
 *
 */
@Service
@Transactional
public class ViewerServiceImpl implements ViewerService{

	// The @Service annotation allows Spring to use the defined Transaction mananger which knows how to handle the Hibernate transactions
	
	@Autowired 
	private ViewerDAO viewerDao;
	private AdminDAO adminDao;

	
	/**
	 * Method to return a certain number of the top viewed  games of a specific platform or all platforms for the last 30 days
	 * @param numGames The number of top viewed games to find
	 * @platformId The id of the platform whose games are to be returned
	 * @return Returns a List of ViewedGames objects containing the top viewed games  
	 */
	public List<ViewedGames> getViewsTopMonthlyGames(int numGames, int platformId){
		return viewerDao.getViewsTopMonthlyGames(numGames, platformId);
	}

	
	/**
	 * Method to return a list of all Platforms from the Platform table.
	 * @return Returns a List of Platform objects representing the platfornms that are in the database
	 */
	public List<Platform> getPlatforms(){
		return viewerDao.getPlatforms();
	}

	
	/**
	 * Method that receives a list of ViewedGames objects, finds the Game Covers associated with the games and adds the games' Game Covers 
	 * to the ViewedGame objects and then return the ViewedGame objects as a list 
	 * @param topGames A List of ViewedGames objects whose game covers have to be added
	 * @return Returns the List of ViewedGames objects after adding a List og Game Covers to each game in the passed in List
	 */
	public List<ViewedGames> getGamesCovers(List<ViewedGames> topGames, String type){
		return viewerDao.getGamesCovers(topGames, type);
	}
	
	
 	/**
 	 * Method to do a search of games based on a platform and part of the Game name
 	 * @param plat The id of the platform to search
 	 * @param searchName The name, or part of the name, to search for
 	 * @return Returns a List of Game objects from the database that matched the passed in search criteria
 	 */
	public List<Game> doSearch(int plat, String searchName){
		return viewerDao.doSearch(plat, searchName);
	}
	
	
	/**
	 * Method to return the location of the cover of a particular size for a game that is passed as a parameter
	 * @param id The id of the game who's cover we want to receive
	 * @param size The size of the cover to return
	 * @return Returns the location of the cover as a string
	 */
	public String getGameCover(int id, String size){
		return viewerDao.getGameCover(id, size);
	}

}
