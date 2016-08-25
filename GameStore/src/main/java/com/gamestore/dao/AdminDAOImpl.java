package com.gamestore.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

import javax.mail.*;
import javax.mail.internet.*;

import com.gamestore.model.Game;
import com.gamestore.model.GameCover;
import com.gamestore.model.GameScreens;
import com.gamestore.model.IncreaseBalance;
import com.gamestore.model.Logs;
import com.gamestore.model.MonthlyOrders;
import com.gamestore.model.OrderedGames;
import com.gamestore.model.Orders2;
import com.gamestore.model.Orders30Days;
import com.gamestore.model.OrdersByLevel;
import com.gamestore.model.Platform;
import com.gamestore.model.User;
import com.gamestore.model.Orders;
import com.gamestore.model.Views2;


/**
 * This class implements all of the methods necessary for interaction with the database for a user or an admin 
 * @author Robert Forde
 *
 */
@Repository
public class AdminDAOImpl implements AdminDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	
	/**
	 * This method finds and retrieves the top number of games that have been ordered for a particular platform or for all platforms
	 * @param numGames The number of games to be returned in a List
	 * @param platformId The platform whose games are to be returned
	 * @return Returns a List of the top number(requested) of games for a particular platform 
	 */
	@SuppressWarnings("unchecked")
	public List<OrderedGames> getTopOrderGamesPlatform(int numGames, int platformId){
		
		// First check if we need to limit the games (whose orders are being counted to a particular platform
		DetachedCriteria gamesForPlatform = DetachedCriteria.forClass(Game.class)
				.setProjection(Property.forName("id"));
		if(platformId != 0) {
			gamesForPlatform.add( Property.forName("platformId").eq(platformId) );
		}
		
		// First create a detatched query to only retrieve the delivered orders
		DetachedCriteria deliveredOrders = DetachedCriteria.forClass(Orders.class)
				.setProjection(Property.forName("id"));
		deliveredOrders.add( Property.forName("status").eq("DELIVERED") );

		
		// Create a criteria that will group the order records 
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		

		if(platformId != 0) {
			// Use the detached query (gamesForPlatform) results to restrict the grouping for this platform only
			crit.add(Property.forName("gameId").in((gamesForPlatform)));
		}
		
		// Use the detached query (deliveredOrders) results to restrict the grouping for only delivered orders
		crit.add(Property.forName("id").in((deliveredOrders)));
		
		// Use Projections to do the grouping and counting and use ResultTransformer to make use of the OrderedGames Entity so 
		// we can have a List of the resulting objects so as to display them easily on a JSP page.
		crit.setProjection(Projections.projectionList()
				.add(Projections.count("id").as("count"))
				.add(Projections.groupProperty("game").as("orderedGame"))
				.add(Projections.groupProperty("gameId").as("gId")));
		crit.setResultTransformer(new AliasToBeanResultTransformer(OrderedGames.class));

		// Sort the list by the most ordered games and only pick the top 5 
		crit.addOrder(Order.desc("count"));
		crit.setMaxResults(numGames);
		    	
		List<OrderedGames> listGames = crit.list();

			
		return listGames;
	}
	
	
	/**
	 * Method to return a platform name based on a provided platform id
	 * @param platform The ID of the platform
	 * @return The name of the platform
	 */
	public String getPlatformName(int platform){
		
		String strPlatform = null;
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Platform.class);
		crit.add(Restrictions.eq("id", platform));
		Platform p = (Platform)crit.uniqueResult();
		
		if(p != null) {
			strPlatform = p.getName();
		}
		
		return (strPlatform);
	}
	
	
	/**
	 * This method returns a list of total orders per month for a particular year
	 * @param year The year whose months the method is to be ran for
	 * @return A List of MonthlyOrder objects with a count for each month
	 */
	@SuppressWarnings("unchecked")
	public List<MonthlyOrders> getOrdersByMonth(int year){
		
		// First create a detatched query to only retrieve the delivered orders for this year
		DetachedCriteria deliveredOrders = DetachedCriteria.forClass(Orders.class)
				.setProjection(Property.forName("id"));
		deliveredOrders.add( Property.forName("status").eq("DELIVERED") );
		deliveredOrders.add( Property.forName("orderYear").eq(year) );

		
		// Create a criteria that will group the order records 
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		
		
		// Use the detached query (gamesForPlatform) results to restrict the grouping for only delivered orders this year
		crit.add(Property.forName("id").in((deliveredOrders)));
		
		// Use Projections to do the grouping and counting and use ResultTransformer to make use of the MonthlyOrders Class so 
		// we can have a List of the resulting objects so as to display them easily on a JSP page.
		crit.setProjection(Projections.projectionList()
				.add(Projections.count("id").as("count"))
				.add(Projections.groupProperty("orderMonth").as("month")));
		crit.setResultTransformer(new AliasToBeanResultTransformer(MonthlyOrders.class));

		
		// Sort the list by the month numbers 
		crit.addOrder(Order.asc("month"));

		List<MonthlyOrders> listMonths = crit.list();
		
		
		return listMonths;

	}
	
	
	/**
	 * Method to find and return the orders over the last 30 days
	 * @return A List of Orders30Days objects with a count for each of the last 30 days
	 */
	@SuppressWarnings("unchecked")
	public List<Orders30Days> getOrdersTotal30Days(){

		// Get the date of 30 days ago for the query
		Date d = new Date();
		long days30 = 1000*60*60*24*30L;
		Date last30days = new Date(d.getTime() - (days30));
		
		
		// Create a detatched query to only count the orders over the last 30 days
		DetachedCriteria ordersLast30Days = DetachedCriteria.forClass(Orders.class)
			.setProjection(Property.forName("id"));
		ordersLast30Days.add( Restrictions.ge("date", last30days) );
				
			
		// Create a criteria that will group the order records 
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
				
				
		// Use the detached query (gamesForPlatform) results to restrict the grouping for only delivered orders this year
		crit.add(Property.forName("id").in((ordersLast30Days)));
				
		// Use Projections to do the grouping and counting and use ResultTransformer to make use of the Orders30Days Class so 
		// we can have a List of the resulting objects so as to display them easily on a JSP page.
		crit.setProjection(Projections.projectionList()
			.add(Projections.count("id").as("count"))
			.add(Projections.groupProperty("date").as("date")));
		crit.setResultTransformer(new AliasToBeanResultTransformer(Orders30Days.class));

				
		// Sort the list by the date 
		crit.addOrder(Order.asc("date"));

		List<Orders30Days> list30Days = crit.list();

		
		return list30Days;
	}
	

	/**
	 * Method to return anOrdersByLevel object with an order count for each level
	 * @param level The user level (Gold, Silver, Bronze)
	 * @return Returns an OrdersByLevel object which contains the level and the count f it's orders
	 */
	@SuppressWarnings("unchecked")
	public OrdersByLevel getOrdersByLevel(String level){

		// Create a detatched query to pull all the User ids with the level required
		DetachedCriteria usersLevel = DetachedCriteria.forClass(User.class)
			.setProjection(Property.forName("id"));
		usersLevel.add( Restrictions.eq("level", level) );
				
			
		// Create a criteria that will group the order records 
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
				
				
		// Use the detached query (usersLevel) results to restrict the grouping for only the Users of the required level
		crit.add(Property.forName("userId").in((usersLevel)));
				
		// Use Projections to do the grouping and counting and use ResultTransformer to make use of the OrdersByLevel Class so 
		// we can have a List of the resulting objects so as to display them easily on a JSP page.
		crit.setProjection(Projections.projectionList()
			.add(Projections.count("id").as("count")));
		crit.setResultTransformer(new AliasToBeanResultTransformer(OrdersByLevel.class));
				
		List<OrdersByLevel> ordersLevel = crit.list();

		// Put OrdersByLevel record in a new OrdersByLevel object and assign it's level as per requested level
		OrdersByLevel resultsOrdersByLevel=null;
		for(OrdersByLevel o: ordersLevel){
			o.setLevel(level);
			resultsOrdersByLevel = o;
		}
				
		return resultsOrdersByLevel;
	}

	
	/**
	 * Method to return a MDS encrypted string based on the string passed
	 * @param string The string to be encrypted
	 * @return The encrypted string
	 */
	public String encrypt(String string){
		
		String passwordToHash = string;
        String encrypted = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            encrypted = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        
        return encrypted;
		
	}
	

	/**
	 * Method to search for a user in the User table based on a passed user and e-mail and return it or null
	 * @param email The email to search for
	 * @param password The password to search for
	 * @return A User object containg the User that was found or null if not found  
	 */
	public User checkLogin(String email, String password){
		
		password = encrypt(password);

		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("email", email));
		crit.add(Restrictions.eq("password", password));
		User user = (User)crit.uniqueResult();

		return user;
		
	}
	
	
	/**
	 * Method to save a new registered User to the database
	 * @param user The User Object to be saved
	 */
	public void registerUser(User user){
		
		sessionFactory.getCurrentSession().save(user);
	}
	

	/**
	 * Method to gather all of the orders to be Approved (ORDERED) into an ArrayList of Orders and return them
	 * @return A List of Order objects representing the orders to be approved from the database
	 */
	@SuppressWarnings("unchecked")
	public List<Orders> getOrdersForApproval(){
		
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.eq("status", "ORDERED"));
		List<Orders> listOrders = crit.list();
				
		return listOrders;

	}
	
	
	/**
	 * Method to gather all of the orders to be Delivered (APPROVED) into an ArrayList of Orders and return them
	 * @return A List of Order objects representing the orders to be approved from the database 
	 */
	@SuppressWarnings("unchecked")
	public List<Orders> getOrdersForDelivery(){
		
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.eq("status", "APPROVED"));
		List<Orders> listOrders = crit.list();
		
		return listOrders;
	}
	
	
	/**
	 * Method to gather all of the orders that can be deleted (ORDERED, APPROVED) into an ArrayList of Orders and return them
	 * @return A List of Order objects representing the orders to be approved from the database 
	 */
	@SuppressWarnings("unchecked")
	public List<Orders> getOrdersDeletion(){
		
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.or(Restrictions.eq("status", "ORDERED"),Restrictions.eq("status", "APPROVED")));
		List<Orders> listOrders = crit.list();
				
		return listOrders;
	}

	
	/**
	 * Method to gather all of the users to be approved (APPROVED="No") into an ArrayList of Users and return them
	 * @return A List of User objects representing the users to be approved from the database 
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersApprove(){

		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("approved", "No"));
		List<User> listUsers = crit.list();
				
		return listUsers;
	}
	
	
	/**
	 * The method first finds the order in the database and gets it's user's balance. It then gets the appropriate price of the game based 
	 * on the user's level. It ehn decreases the User's balance by the appropriate price, logs an order approval transaction in the logs 
	 * table and sets the order as approved.  
	 * @param orderId The id of the order to be set to approved
	 */
	public void orderSetApproved(int orderId){

		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.eq("id", orderId));
		Orders order = (Orders)crit.uniqueResult();
		
		// Get the the price and the user balance
		float balance = order.getUser().getBalance();
		String level = order.getUser().getLevel();
		float price = 0.00f;
		if (level.compareTo("Bronze") == 0){
			price = order.getGame().getBronzePrice();
			
		} else if(level.compareTo("Silver") == 0){
			price = order.getGame().getSilverPrice();
			
			
		}else if(level.compareTo("Gold") == 0){
			price = order.getGame().getGoldPrice();
			
		}
		
		
		// Decrease the User's balance based on the price of the game
		order.getUser().setBalance(balance - price);
		
		// Log the transaction
		Logs log = new Logs();
		log.setDate(new Date());
		log.setUserId(order.getUserId());
		log.setEmail(order.getUser().getEmail());
		log.setTransaction("ORDER APPROVAL");
		log.setGameName(order.getGame().getName());
		log.setAmount(price);
		log.setBalanceAfter(balance-price);
		
		// Set the order status to approved
		order.setStatus("APPROVED");
		
		sessionFactory.getCurrentSession().save(order);
		sessionFactory.getCurrentSession().save(log);
		
	}
	
	
	/**
	 * The method first retrieves the order from the database and gets it's user's balance and also it's appropriate price based on it's 
	 * user's level. It then logs this order delivery in the logs table and sets the order as delivered.   
	 * @param orderId The id of the order to be set as delivered
	 */
	public void orderSetDelivered(int orderId){
		
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.eq("id", orderId));
		Orders order = (Orders)crit.uniqueResult();

		// Get the the price and the user balance
		float balance = order.getUser().getBalance();
		String level = order.getUser().getLevel();
		float price = 0.00f;
		if (level.compareTo("Bronze") == 0){
		price = order.getGame().getBronzePrice();
						
		} else if(level.compareTo("Silver") == 0){
			price = order.getGame().getSilverPrice();
						
		}else if(level.compareTo("Gold") == 0){
			price = order.getGame().getGoldPrice();
						
		}
		
		
		// Log the delivered in the Logs table
		Logs log = new Logs();
		log.setDate(new Date());
		log.setUserId(order.getUserId());
		log.setEmail(order.getUser().getEmail());
		log.setTransaction("DELIVERED");
		log.setGameName(order.getGame().getName());
		log.setAmount(price);
		log.setBalanceAfter(balance);
					
		sessionFactory.getCurrentSession().save(log);
		
		
		// Change the order status to be Delivered.
		order.setStatus("DELIVERED");
		
		sessionFactory.getCurrentSession().save(order);
	}

	
	/**
	 * The method first finds the order in the database. It checks if the order has been approved and if it has then it finds it's price 
	 * based on the user's level and increases the user's balance and logs the deletion in the logs table. Regardless of the order's status 
	 * it sets the order as being deleted.
	 * @param orderId The id of the order to be set as deleted
	 */
	public void orderSetDeleted(int orderId){
		
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.eq("id", orderId));
		Orders order = (Orders)crit.uniqueResult();
				
		// If we are deleting an approved order then we need to find the price and increase the user's balance.
		if(order.getStatus().compareTo("APPROVED") == 0){
			
			// Get the the price and the user balance
			float balance = order.getUser().getBalance();
			String level = order.getUser().getLevel();
			float price = 0.00f;
			if (level.compareTo("Bronze") == 0){
				price = order.getGame().getBronzePrice();
				
			} else if(level.compareTo("Silver") == 0){
				price = order.getGame().getSilverPrice();
				
			}else if(level.compareTo("Gold") == 0){
				price = order.getGame().getGoldPrice();
				
			}
			
			// Increase the user's balance
			order.getUser().setBalance(balance + price);
			
			// Balance changed so insert a log in the Logs table
			Logs log = new Logs();
			log.setDate(new Date());
			log.setUserId(order.getUserId());
			log.setEmail(order.getUser().getEmail());
			log.setTransaction("APPROVAL DELETION");
			log.setGameName(order.getGame().getName());
			log.setAmount(price);
			log.setBalanceAfter(balance+price);
			
			sessionFactory.getCurrentSession().save(log);
		}
		
		// Change the order status to deleted
		order.setStatus("DELETED");
				
		sessionFactory.getCurrentSession().save(order);
		
	}
	

	/**
	 * Method to check/change the value of delivered orders of a User from an OrderId and see if the user's level needs to be changed
	 * @param orderId
	 */
	@SuppressWarnings("unchecked")
	public void checkUserLevel(int orderId){
	
		// Check if the user's level needs to change
		// 0 		- 500		Bronze
		// 500		- 1,500		Silver
		// 1,500	+			Gold
		
		
		// First get the Order from the orderId
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Orders.class);
		crit.add(Restrictions.eq("id", orderId));
		Orders order = (Orders)crit.uniqueResult();
		
		// Get the User level from the order's userId
		crit  = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("id", order.getUserId()));
		User user = (User)crit.uniqueResult();
		
		String level = user.getLevel();
		
		
		// Get the DELIVERED logs from the order's UserId
		crit  = sessionFactory.getCurrentSession().createCriteria(Logs.class);
		crit.add(Restrictions.and(Restrictions.eq("userId", order.getUserId()),Restrictions.eq("transaction", "DELIVERED")));
		List<Logs> listLogs = (List<Logs>)crit.list();
		
		// Calculate the order value that has been delivered
		float totalAmount = 0.00f;
		for(Logs l: listLogs) {
			totalAmount += l.getAmount();
		}
		
		
		// Check if user level needs to change and change it if it does.
		if(totalAmount > 1500 && level.compareTo("Gold") != 0){
			user.setLevel("Gold");
			sessionFactory.getCurrentSession().save(user);
			
		} else if (totalAmount > 500 && level.compareTo("Silver") != 0){
			user.setLevel("Silver");
			sessionFactory.getCurrentSession().save(user);	
			
		}
		
		
	}
	
	
	/**
	 * Method to approve a new user on the system, giving them a Bronze level and set Approved to "Yes" and send an e-mail to inform the 
	 * user
	 * @param userId The id of the User to be approved
	 */
	public void userSetApproved(int userId){

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("id", userId));
		
		User user = (User)crit.uniqueResult();
		
		// Set level to Bronze
		user.setLevel("Bronze");
		
		// Set Approved to "Yes"
		user.setApproved("Yes");
		
		// Save the changes
		sessionFactory.getCurrentSession().save(user);
		
		
		// Send an email to the new User notifying them that they have been approved
		
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		final String username = "alroworldgames@gmail.com";//
		final String password = "administrator54321";
		try{
			Session session = Session.getDefaultInstance(props, new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
		        }});

		   // -- Create a new message --
		   Message msg = new MimeMessage(session);

		   // -- Set the FROM and TO fields --
		   msg.setFrom(new InternetAddress("alroworldgames@gmail.com"));
		   msg.setRecipients(Message.RecipientType.TO, 
		   InternetAddress.parse(user.getEmail(),false));
		   msg.setSubject("Approved!");
		   msg.setContent("<p><img src='http://www.robertforde.eu/logo.png' /></p>"
				   + "<h1>Welcome to ALRO Games</h1>"
				   + "<p>You have been approved and can now login !</p>"
				   + "<p>Click below to login</p>"
				   + "<p><a href='http://localhost:8080/GameStore/'>http://localhost:8080/GameStore/</a></p>","text/html");

		   msg.setSentDate(new Date());
		   Transport.send(msg);
		}catch (MessagingException e){ 
			System.out.println("Error: " + e);
		}
		
	}
	
	
	/**
	 * Method to set the increase to approved and to increase the user's balance and to create a log
	 * @param userId The id of the User who is requesting the increase
	 * @param increaseId The id of the increase balance request
	 */
	public void increaseBalanceSetApproved(int userId, int increaseId){
		
		// First set the Approved status on the increasebalance table
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(IncreaseBalance.class);
		crit.add(Restrictions.eq("id", increaseId));
		IncreaseBalance increaseBalance = (IncreaseBalance)crit.uniqueResult();
		
		// Set Approved to "Yes"
		increaseBalance.setApproved("Yes");
		
		
		//Next increase the User's balance on the user table
		crit = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("id", userId));
		User user = (User)crit.uniqueResult();
				
		// Increase the User's balance
		float newBalance = user.getBalance() + increaseBalance.getIncrease();
		user.setBalance(newBalance);
		
		
		// Log the increase
		Logs log = new Logs();
		log.setDate(new Date());
		log.setUserId(user.getId());
		log.setEmail(user.getEmail());
		log.setTransaction("INCREASE BALANCE");
		log.setGameName("");
		log.setAmount(increaseBalance.getIncrease());
		log.setBalanceAfter(newBalance);
		
		
		//Save all of the changes
		sessionFactory.getCurrentSession().save(log);
		sessionFactory.getCurrentSession().save(user);
		sessionFactory.getCurrentSession().update(increaseBalance);
		
		
		// Email the User notifying them that their balance has been increased
		
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		  // Get a Properties object
	     Properties props = System.getProperties();
		 props.setProperty("mail.smtp.host", "smtp.gmail.com");
		 props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		 props.setProperty("mail.smtp.socketFactory.fallback", "false");
		 props.setProperty("mail.smtp.port", "465");
		 props.setProperty("mail.smtp.socketFactory.port", "465");
		 props.put("mail.smtp.auth", "true");
		 props.put("mail.debug", "true");
		 props.put("mail.store.protocol", "pop3");
		 props.put("mail.transport.protocol", "smtp");
		 final String username = "alroworldgames@gmail.com";//
		 final String password = "administrator54321";
		 try{
		     Session session = Session.getDefaultInstance(props, new Authenticator(){
		    	 					protected PasswordAuthentication getPasswordAuthentication() {
		    	 						return new PasswordAuthentication(username, password);
		                            }});

		     // -- Create a new message --
		     Message msg = new MimeMessage(session);

		     // -- Set the FROM and TO fields --
		     msg.setFrom(new InternetAddress("alroworldgames@gmail.com"));
		     msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail(),false));
		     msg.setSubject("Balance Increased!");
		     msg.setContent("<p><img src='http://www.robertforde.eu/logo.png' /></p>"
				+ "<h1>ALRO Games - Balance Increase</h1>"
				+ "<p>Your balance has been increased at ALRO Games !</p>"
				+ "<p>Click below to login</p>"
				+ "<p><a href='http://localhost:8080/GameStore/'>http://localhost:8080/GameStore/</a></p>","text/html");

		     msg.setSentDate(new Date());
		     Transport.send(msg);
		  }catch (MessagingException e){ 
			  System.out.println("Error: " + e);
		  }
	}
	
	
	/**
	 * Method to return a List of games, for a given platform, that have been searched for
	 * @param plat The id of the platform to be searched for
	 * @param searchName The name, or part of the name, of the game to be searched for
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
	 * Method to return the location of a cover for a game
	 * @param id The id of the game
	 * @param The size of the cover whose location to be found
	 * @return Returns a String containing the location of the cover
	 */
	public String getGameCover(int id, String size){

		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(GameCover.class);
		crit.add(Restrictions.eq("gameId", id));
		crit.add(Restrictions.eq("type", size));
		
		GameCover gameCover =  (GameCover)crit.uniqueResult();
		
		String cover = gameCover.getLocation();
		
		return cover;
	}
	
	
	/**
	 * Method to save an order of a given gameId for a given userId. Save a log in the log table and set the user's level
	 * @param userId The id of the user placing the order
	 * @param gameId The id of the game that is being ordered
	 */
	public void saveOrder(int userId, int gameId){

		// Get today's date and day of month and month of year and the year
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		
		// Create an Orders record and Save it to the Orders table
		Orders2 order = new Orders2();
		order.setUserId(userId);
		order.setGameId(gameId);
		order.setStatus("ORDERED");
		order.setDate(new Date());
		order.setOrderDay(day);
		order.setOrderMonth(month);
		order.setOrderYear(year);
		

		sessionFactory.getCurrentSession().save(order);
		
		
		// Get User and Game
		User user = getUser(userId);
		Game game = getGame(gameId);
		
		float price;
		
		if(user.getLevel().compareTo("Bronze")==0){
			price = game.getBronzePrice();
			
		}else if(user.getLevel().compareTo("Silver")==0){
			price = game.getSilverPrice();
		
		} else{
			price = game.getGoldPrice();
			
		}
		
		// Order saved so insert a log in the Logs table
		Logs log = new Logs();
		log.setDate(new Date());
		log.setUserId(userId);
		log.setEmail(user.getEmail());
		log.setTransaction("ORDERED");
		log.setGameName(game.getName());
		log.setAmount(price);
		log.setBalanceAfter(user.getBalance());
					
		sessionFactory.getCurrentSession().save(log);

	}
	
	
	/**
	 * Method to return a User based on a passed in id
	 * @param userId The id of the user to be retrieved
	 * @return Returns a User object containing the details of the User retrieved
	 */
	public User getUser(int userId){
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("id", userId));
		
		User user = (User)crit.uniqueResult();
				
		return user;
	}

	
	/**
	 * Method to return a Game based on a gameId
	 * @param gameId The id of the game to be returned
	 * @return Returns a Game object representing the game in the database
	 */
	public Game getGame(int gameId){
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Game.class);
		crit.add(Restrictions.eq("id", gameId));
		
		Game game = (Game)crit.uniqueResult();
		
		return game;
	}
	

	/**
	 * Method to save an IncreaseBalance record to the IncreaseBalance Table in the Database
	 * @param increase The IncreaseBalance object to be saved to the database
	 */
	public void saveIncreaseBalance(IncreaseBalance increase){

		sessionFactory.getCurrentSession().save(increase);
	}


	/**
	 * Method to update a User in the database
	 * @param user The User object to be updated
	 */
	public void updateUser(User user){
		
		sessionFactory.getCurrentSession().update(user);
	}
	
	
	/**
	 * Method to retrieve a user's logs in a List
	 * @param userId The id of the user whos logs are to be retrieved
	 * @return Returns a list of Log objects representing the logs for the passed in user 
	 */
	@SuppressWarnings("unchecked")
	public List<Logs> getUserLogs(int userId) { 
		
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(Logs.class);
		crit.add(Restrictions.eq("userId", userId));

		List<Logs> listLogs = crit.list();

		return listLogs;
		
	}
	
	
	/**
	 * Method to return an List of User e-mails of users that have been approved
	 * @return Returns a list of User objects
	 */
	@SuppressWarnings("unchecked")
	public List<User> getApprovedEmails(){
			
		Criteria crit  = sessionFactory.getCurrentSession().createCriteria(User.class);

		crit.add(Restrictions.eq("approved", "Yes"));
		List<User> listUsers = (List<User>)crit.list();
		
		return listUsers;
	}
		
	
	/**
	 * Method to return a user's e-mail as a String
	 * @param userId The id of the user whos e-mail is to be returned	
	 */
	public String getUserEmail(int userId){
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("id", userId));
		User user = (User)crit.uniqueResult();
		
		String email = user.getEmail();
			
		return email;
	}
	
	
	/**
	 * Method to return a List of IncreaseBalances that have to be approved
	 * @return Returns a list of IncreaseBalance objects 
	 */
	@SuppressWarnings("unchecked")
	public List<IncreaseBalance> getIncreaseBalances(){
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(IncreaseBalance.class);
		crit.add(Restrictions.eq("approved", "No"));
		List<IncreaseBalance> listIncreaseBalance = (List<IncreaseBalance>)crit.list();
		
		
		return listIncreaseBalance;
	}
	

	/**
	 * Method to add a view to the views table for the game/user passed
	 * @param gameId The id of the game that was viewed
	 * @param userId The id of the user that viewed the game
	 */
	public void addView(int gameId, int userId){
			
		Views2 view = new Views2();
		
		view.setGameId(gameId);
		view.setUserId(userId);
		view.setDate(new Date());

		sessionFactory.getCurrentSession().save(view);
	}

	
	/**
	 * Method to get a list of gamescreens for a game from the database
	 * @param gameId The id of the game whos screens are to be returned
	 * @return Returns a list of GameScreens objects that hold the screens for the game
	 */
	@SuppressWarnings("unchecked")
	public List<GameScreens> getGameScreens(int gameId){
		
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(GameScreens.class);
		crit.add(Restrictions.eq("id", gameId));
		List<GameScreens> gameScreens = (List<GameScreens>)crit.list();
		
		return gameScreens;
	}
}
