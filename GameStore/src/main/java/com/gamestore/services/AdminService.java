package com.gamestore.services;

import java.util.List;

import com.gamestore.model.Game;
import com.gamestore.model.GameScreens;
import com.gamestore.model.IncreaseBalance;
import com.gamestore.model.Logs;
import com.gamestore.model.MonthlyOrders;
import com.gamestore.model.OrderedGames;
import com.gamestore.model.Orders;
import com.gamestore.model.Orders30Days;
import com.gamestore.model.OrdersByLevel;
import com.gamestore.model.User;

public interface AdminService {

	/**
	 * This method finds and retrieves the top number of games that have been ordered for a particular platform or for all platforms
	 * @param numGames The number of games to be returned in a List
	 * @param platformId The platform whose games are to be returned
	 * @return Returns a List of the top number(requested) of games for a particular platform 
	 */
	public List<OrderedGames> getTopOrderGamesPlatform(int numGames, int platformId);
	
	/**
	 * Method to return a platform name based on a provided platform id
	 * @param platform The ID of the platform
	 * @return The name of the platform
	 */
	public String getPlatformName(int platform);
	
	/**
	 * This method returns a list of total orders per month for a particular year
	 * @param year The year whose months the method is to be ran for
	 * @return A List of MonthlyOrder objects with a count for each month
	 */
	public List<MonthlyOrders> getOrdersByMonth(int year);
	
	/**
	 * Method to find and return the orders over the last 30 days
	 * @return A List of Orders30Days objects with a count for each of the last 30 days
	 */
	public List<Orders30Days> getOrdersTotal30Days();
	
	/**
	 * Method to return anOrdersByLevel object with an order count for each level
	 * @param level The user level (Gold, Silver, Bronze)
	 * @return Returns an OrdersByLevel object which contains the level and the count f it's orders
	 */
	public OrdersByLevel getOrdersByLevel(String level);
	
	/**
	 * Method to return a MDS encrypted string based on the string passed
	 * @param string The string to be encrypted
	 * @return The encrypted string
	 */ 
	public String encrypt(String string);
	
	/**
	 * Method to search for a user in the User table based on a passed user and e-mail and return it or null
	 * @param email The email to search for
	 * @param password The password to search for
	 * @return A User object containg the User that was found or null if not found  
	 */ 
	public User checkLogin(String email, String password);

	/**
	 * Method to save a new registered User to the database
	 * @param user The User Object to be saved
	 */
	public void registerUser(User user);
	
	/**
	 * Method to gather all of the orders to be Approved (ORDERED) into an ArrayList of Orders and return them
	 * @return A List of Order objects representing the orders to be approved from the database
	 */
	public List<Orders> getOrdersForApproval();
	
	/**
	 * Method to gather all of the orders to be Delivered (APPROVED) into an ArrayList of Orders and return them
	 * @return A List of Order objects representing the orders to be approved from the database 
	 */
	public List<Orders> getOrdersForDelivery();
	
	/**
	 * Method to gather all of the orders that can be deleted (ORDERED, APPROVED) into an ArrayList of Orders and return them
	 * @return A List of Order objects representing the orders to be approved from the database 
	 */
	public List<Orders> getOrdersDeletion();
	
	/**
	 * Method to gather all of the users to be approved (APPROVED="No") into an ArrayList of Users and return them
	 * @return A List of User objects representing the users to be approved from the database 
	 */
	public List<User> getUsersApprove();
	
	/**
	 * The method first finds the order in the database and gets it's user's balance. It then gets the appropriate price of the game based 
	 * on the user's level. It ehn decreases the User's balance by the appropriate price, logs an order approval transaction in the logs 
	 * table and sets the order as approved.  
	 * @param orderId The id of the order to be set to approved
	 */
	public void orderSetApproved(int orderId);
	
	/**
	 * The method first retrieves the order from the database and gets it's user's balance and also it's appropriate price based on it's 
	 * user's level. It then logs this order delivery in the logs table and sets the order as delivered.   
	 * @param orderId The id of the order to be set as delivered
	 */
	public void orderSetDelivered(int orderId);
	
	/**
	 * The method first finds the order in the database. It checks if the order has been approved and if it has then it finds it's price 
	 * based on the user's level and increases the user's balance and logs the deletion in the logs table. Regardless of the order's status 
	 * it sets the order as being deleted.
	 * @param orderId The id of the order to be set as deleted
	 */
	public void orderSetDeleted(int orderId);
	
	/**
	 * Method to check/change the value of delivered orders of a User from an OrderId and see if the user's level needs to be changed
	 * @param orderId
	 */ 
	public void checkUserLevel(int orderId);
	
	/**
	 * Method to approve a new user on the system, giving them a Bronze level and set Approved to "Yes" and send an e-mail to inform the 
	 * user
	 * @param userId The id of the User to be approved
	 */
	public void userSetApproved(int userId);
	
	/**
	 * Method to set the increase to approved and to increase the user's balance and to create a log
	 * @param userId The id of the User who is requesting the increase
	 * @param increaseId The id of the increase balance request
	 */
	public void increaseBalanceSetApproved(int userId, int increase);
	
	/**
	 * Method to return a List of games, for a given platform, that have been searched for
	 * @param plat The id of the platform to be searched for
	 * @param searchName The name, or part of the name, of the game to be searched for
	 */
	public List<Game> doSearch(int plat, String searchName);

	/**
	 * Method to return the location of a cover for a game
	 * @param id The id of the game
	 * @param The size of the cover whose location to be found
	 * @return Returns a String containing the location of the cover
	 */
	public String getGameCover(int id, String size);
	
	/**
	 * Method to save an order of a given gameId for a given userId. Save a log in the log table and set the user's level
	 * @param userId The id of the user placing the order
	 * @param gameId The id of the game that is being ordered
	 */
	public void saveOrder(int userId, int gameId);

	/**
	 * Method to return a User based on a passed in id
	 * @param userId The id of the user to be retrieved
	 * @return Returns a User object containing the details of the User retrieved
	 */
	public User getUser(int userId);
	
	/**
	 * Method to return a Game based on a gameId
	 * @param gameId The id of the game to be returned
	 * @return Returns a Game object representing the game in the database
	 */
	public Game getGame(int gameId);
	
	/**
	 * Method to save an IncreaseBalance record to the IncreaseBalance Table in the Database
	 * @param increase The IncreaseBalance object to be saved to the database
	 */
	public void saveIncreaseBalance(IncreaseBalance increase);
	
	/**
	 * Method to update a User in the database
	 * @param user The User object to be updated
	 */
	public void updateUser(User user);
	
	/**
	 * Method to retrieve a user's logs in a List
	 * @param userId The id of the user whos logs are to be retrieved
	 * @return Returns a list of Log objects representing the logs for the passed in user 
	 */
	public List<Logs> getUserLogs(int userId);
	
	/**
	 * Method to return an List of User e-mails of users that have been approved
	 * @return Returns a list of User objects
	 */
	public List<User> getApprovedEmails();
	
	/**
	 * Method to return a user's e-mail as a String
	 * @param userId The id of the user whos e-mail is to be returned	
	 */
	public String getUserEmail(int userId);
	
	/**
	 * Method to return a List of IncreaseBalances that have to be approved
	 * @return Returns a list of IncreaseBalance objects 
	 */
	public List<IncreaseBalance> getIncreaseBalances();
	
	/**
	 * Method to add a view to the views table for the game/user passed
	 * @param gameId The id of the game that was viewed
	 * @param userId The id of the user that viewed the game
	 */
	public void addView(int gameId, int userId);
	
	/**
	 * Method to get a list of gamescreens for a game from the database
	 * @param gameId The id of the game whos screens are to be returned
	 * @return Returns a list of GameScreens objects that hold the screens for the game
	 */
	public List<GameScreens> getGameScreens(int gameId);
}
