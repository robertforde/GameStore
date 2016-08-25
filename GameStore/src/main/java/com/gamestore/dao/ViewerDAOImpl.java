package com.gamestore.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gamestore.model.Game;
import com.gamestore.model.GameCover;
import com.gamestore.model.Platform;
import com.gamestore.model.ViewedGames;
import com.gamestore.model.Views;


/**
 * This class implements all of the methods necessary for interaction with the database for a viewer
 * @author Robert Forde
 *
 */
@Repository
public class ViewerDAOImpl implements ViewerDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	
	/**
	 * Method to return a certain number of the top viewed  games of a specific platform or all platforms for the last 30 days
	 * @param numGames The number of top viewed games to find
	 * @platformId The id of the platform whose games are to be returned
	 * @return Returns a List of ViewedGames objects containing the top viewed games  
	 */
	public List<ViewedGames> getViewsTopMonthlyGames(int numGames, int platformId){
		
		// Get the date of 30 days ago for the query
		Date d = new Date();
		long days30 = 1000*60*60*24*30L;
		Date last30days = new Date(d.getTime() - (days30));
		
		// First get a detatched query of all games on this platform
		DetachedCriteria gamesForPlatform = DetachedCriteria.forClass(Game.class)
			.setProjection(Property.forName("id"));
		if(platformId != 0) {
			gamesForPlatform.add( Property.forName("platformId").eq(platformId) );
		}

		// Create a detatched query to only count the views over the last 30 days
		DetachedCriteria viewsLast30Days = DetachedCriteria.forClass(Views.class)
				.setProjection(Property.forName("id"));
		viewsLast30Days.add( Restrictions.ge("date", last30days) );
		
		
		// Create a criteria that will group the views records 
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Views.class);

		// Use the detached query (gamesForPlatform) results to restrict the grouping for this platform only
		crit.add(Property.forName("gameId").in((gamesForPlatform)));

		
		// Use the detached query (viewsLast30Days) results to restrict the grouping to only views in the last 30 days
		crit.add(Property.forName("id").in((viewsLast30Days)));

		// Use Projections to do the grouping and counting and use ResultTransformer to make use of the ViewdGames Entity so we can 
		// have a List of the resulting objects so as to display them easily on a JSP page.
		crit.setProjection(Projections.projectionList()
				.add(Projections.count("id").as("count"))
				.add(Projections.groupProperty("game").as("viewsGame"))
				.add(Projections.groupProperty("gameId").as("gId")));
		crit.setResultTransformer(new AliasToBeanResultTransformer(ViewedGames.class));

		// Sort the list by the most viewed games and only pick the top 5 
    	crit.addOrder(Order.desc("count"));
    	crit.setMaxResults(numGames);
    	
    	@SuppressWarnings("unchecked")
		List<ViewedGames> listGames = crit.list();
    	    	    	    	
    	return listGames;
	}
	

	/**
	 * Method to return a list of all Platforms from the Platform table.
	 * @return Returns a List of Platform objects representing the platfornms that are in the database
	 */
	public List<Platform> getPlatforms(){
		
		@SuppressWarnings("unchecked")
		List<Platform> listPlatforms = sessionFactory.getCurrentSession().createCriteria(Platform.class)
			.list();

    	return listPlatforms;
	}
	
	

	/**
	 * Method that receives a list of ViewedGames objects, finds the Game Covers associated with the games and adds the games' Game Covers 
	 * to the ViewedGame objects and then return the ViewedGame objects as a list 
	 * @param topGames A List of ViewedGames objects whose game covers have to be added
	 * @return Returns the List of ViewedGames objects after adding a List og Game Covers to each game in the passed in List
	 */
 	@SuppressWarnings("unchecked")
	public List<ViewedGames> getGamesCovers(List<ViewedGames> topGames, String type){
 		
 		for(ViewedGames vg : topGames){
 			
 			Criteria crit = sessionFactory.getCurrentSession().createCriteria(GameCover.class);
 			crit.add(Restrictions.and(Restrictions.eq("gameId", vg.getgId()), Restrictions.eq("type", type)));
 			List<GameCover> listGameCover = (List<GameCover>)crit.list();
 				    	
	    	for(GameCover gc: listGameCover){
	    		vg.setLocation(gc.getLocation());
	    	}
 		}
 		
 		return topGames;
 	}
    

 	/**
 	 * Method to do a search of games based on a platform and part of the Game name
 	 * @param plat The id of the platform to search
 	 * @param searchName The name, or part of the name, to search for
 	 * @return Returns a List of Game objects from the database that matched the passed in search criteria
 	 */
	public List<Game> doSearch(int plat, String searchName){
		
		// Create a criteria that will do the search 
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Game.class);
		
		// If a platform is selected (Not 'All Platforms')
		if (plat!=0) {
			crit.add(Restrictions.eq("platformId", plat));
		}
		
		if (searchName != null) {
			crit.add(Restrictions.like("name", "%" + searchName + "%"));
		}
		
		@SuppressWarnings("unchecked")
		List<Game> listGames = crit.list();

		// Return the list of searched games
		return listGames;
		
	}
	

	/**
	 * Method to return the location of the cover of a particular size for a game that is passed as a parameter
	 * @param id The id of the game who's cover we want to receive
	 * @param size The size of the cover to return
	 * @return Returns the location of the cover as a string
	 */
	public String getGameCover(int id, String size){

		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(GameCover.class);
		crit.add(Restrictions.eq("gameId", id));
		crit.add(Restrictions.eq("type", size));
		
		String cover = null;
		GameCover gameCover = (GameCover)crit.uniqueResult();
		
		if(gameCover != null){
			cover = gameCover.getLocation();
		}
		
		return cover;
	}
}
