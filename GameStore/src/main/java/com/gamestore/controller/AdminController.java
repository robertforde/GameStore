package com.gamestore.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamestore.model.Game;
import com.gamestore.model.GameDisplay;
import com.gamestore.model.GameScreens;
import com.gamestore.model.IncreaseBalance;
import com.gamestore.model.Logs;
import com.gamestore.model.MonthlyOrders;
import com.gamestore.model.OrderedGames;
import com.gamestore.model.Orders;
import com.gamestore.model.Orders30Days;
import com.gamestore.model.OrdersByLevel;
import com.gamestore.model.User;
import com.gamestore.services.AdminService;


/**
 * This is the controller class that handles all of the operations when an ADMIN or a USER is logged in. It also handles the initial login 
 * page.
 * @author Robert Forde
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	// All /admin redirects will be searched for in this class
	
	// Autowire the AdminService so as to give us methods to the implementation of the AdminService interface 
	// in other words the AdminServiceImpl class
	@Autowired
	private AdminService adminService;

	
	/**
	 * This method runs when a logged in Admin user clicks the HOME link or submits an option on the Analysis Charts dropdown on any of the
	 * Admin pages. The method first checks that an administrator is logged on and then and the retrieves the data necessary for three 
	 * charts to be presented for the chosen platform. It retrieves the data for the top 5 ordered games for the platform, the monthly 
	 * breakdown and the breakdown by User Level (Gold, Silver, Bromze) and sets them as Request Attributes. If a User is logged in then 
	 * they are re-directed to the viewer's home page.
	 * @param session The current Session
	 * @param req The Request Parameters
	 * @param platform The platform chosen by the User
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goAdminHome")
    public String adminHomePage(HttpSession session, HttpServletRequest req, @RequestParam("platform") String platform) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
			
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {
				int p = Integer.parseInt(platform);
				List<OrderedGames> topGames = adminService.getTopOrderGamesPlatform(5, p);
				
						
				Object[][] topXGames = {{"",0},{"",0},{"",0},{"",0},{"",0}};
				int counter = 0;
				for(OrderedGames ordGame: topGames){
					topXGames[counter][0] = ordGame.getOrderedGame().getName();
					topXGames[counter][1] = ordGame.getCount();
					counter++;
				}
				
				// Get the Name of the platform ID 
				String strPlatform = "";
				if(platform.compareTo("0") != 0) {
					strPlatform = adminService.getPlatformName(p);
				}else {
					strPlatform = "ALL PLATFORMS";
				}
				
				
				req.setAttribute("topXGames", topXGames);
				req.setAttribute("platform", strPlatform);
				
				int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				// Get the ArrayList of Months for this year with their number of orders
				List<MonthlyOrders> listMonths = adminService.getOrdersByMonth(currentyear);
		
				
				Object[] monthOrders = {0,0,0,0,0,0,0,0,0,0,0,0};
				counter = 0;
				for(MonthlyOrders months: listMonths){
					monthOrders[counter] = months.getCount();
					counter++;
				}
				
				
				req.setAttribute("monthOrders", monthOrders);
				req.setAttribute("currentyear", currentyear);
		
				
				// Get the ArrayList of Months for this year with their number of orders
				List<Orders30Days> listOrders30Days = adminService.getOrdersTotal30Days();
		
				
				for(Orders30Days days: listOrders30Days){
					Date d = days.getDate();
					String strDay = d.toString();
					strDay = strDay.substring(8,10) + "/" + strDay.substring(5,7);
					days.setStrDate(strDay);
					counter++;
				}
				
				
				req.setAttribute("listOrders30Days", listOrders30Days);
		
				
				// Get the OrdersByLevel object (level&count) of orders for each of the possible users' levels
				OrdersByLevel bronzeOrdersCount = adminService.getOrdersByLevel("Bronze");
				OrdersByLevel silverOrdersCount = adminService.getOrdersByLevel("Silver");
				OrdersByLevel goldOrdersCount = adminService.getOrdersByLevel("Gold");
				
						
				req.setAttribute("bronzeOrdersCount", bronzeOrdersCount);
				req.setAttribute("silverOrdersCount", silverOrdersCount);
				req.setAttribute("goldOrdersCount", goldOrdersCount);
				
				
				return "admin/adminHome";
			
			// User logged in, not an administrator so go back to viewer's page
			} else {
				
				return "viewer/home";
			}
			
		// No one logged in go back to viewer's home page
		} else {
			
			return "viewer/home";
		}
        
    }
	
	
	/**
	 * This method checks that the user logging in is in the database and has been approved. It then redirects them to the correct homepage
	 * It handles the possibility of an Admin being logged in and opening another tab and logging on as someone else 
	 * @param session The current session
	 * @param email The email that has been passed in as a Request Parameter
	 * @param password The password that has been passed in as a Request Parameter
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goLogin")
    public String checkLogin(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password) {

		User user = adminService.checkLogin(email, password);
		
		// If the User is in the database
		if(user != null) {

			// If the User has been approved 
			if(user.getApproved().compareTo("Yes") == 0) {
				
				// Administrator logged in so store user in session and open admin's home page
				if(user.getAccesslevel().equals("Administrator")){
				
					session.setAttribute("userObj", user);
					return "redirect:/admin/goAdminHome2";
					
				// User logged in, so store user in session and call function to decide which user page to display (gold, silver 
				// or bronze)
				} else {
					session.setAttribute("userObj", user);
					return "redirect:/admin/goUserFindHome";
				}
				
			// If the User is new and is waiting approval
			} else {
				return "viewer/unapproved";
				
			}

		} else {
			
			return "viewer/errorLogin";
		}
        
    }


	/**
	 * Method that decides which home page a logged in user should have and then seta that homepage in the session's userHome attribute 
	 * @param session The current session
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goUserFindHome")
	public String findUserHomePage(HttpSession session){
		
		// Find the user's level from the Session variable userObj
		User o = (User) session.getAttribute("userObj");
		String level = o.getLevel();
		
		
		// Decide on the user's home page based on the user's level and set it as a session variable.
		if(level.compareTo("Bronze") == 0) {
			session.setAttribute("userHome", "admin/user_bronze_home");
			return "admin/user_bronze_home";
			
		} else if(level.compareTo("Silver") == 0){
			session.setAttribute("userHome", "admin/user_silver_home");
			return "admin/user_silver_home";
			
		} else if(level.compareTo("Gold") == 0){
			session.setAttribute("userHome", "admin/user_gold_home");
			return "admin/user_gold_home";
			
		}
		
		return "admin/user_bronze_home";
	}
	

	/**
	 * This method runs when an Admin user logs in. The method first assures that an administrator is logged on and then and the retrieves 
	 * the data necessary for three charts to be presented for the chosen platform. It retrieves the data for the top 5 ordered games for 
	 * the platform, the monthly breakdown and the breakdown by User Level (Gold, Silver, Bromze) and sets them as Request Attributes. If 
	 * there happens to be a User already logged in to this session then th User is re-directed to the viewer home page.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goAdminHome2")
    public String adminHomePage2(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
			
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {
				String platform="0";
				int p = 0;
				List<OrderedGames> topGames = adminService.getTopOrderGamesPlatform(5, p);
				
						
				Object[][] topXGames = {{"",0},{"",0},{"",0},{"",0},{"",0}};
				int counter = 0;
				for(OrderedGames ordGame: topGames){
					topXGames[counter][0] = ordGame.getOrderedGame().getName();
					topXGames[counter][1] = ordGame.getCount();
					counter++;
				}
				
				// Get the Name of the platform ID 
				String strPlatform = "";
				if(platform.compareTo("0") != 0) {
					strPlatform = adminService.getPlatformName(p);
				}else {
					strPlatform = "ALL PLATFORMS";
				}
				
				
				req.setAttribute("topXGames", topXGames);
				req.setAttribute("platform", strPlatform);
				
				int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				// Get the ArrayList of Months for this year with their number of orders
				List<MonthlyOrders> listMonths = adminService.getOrdersByMonth(currentyear);
		
				
				Object[] monthOrders = {0,0,0,0,0,0,0,0,0,0,0,0};
				counter = 0;
				for(MonthlyOrders months: listMonths){
					monthOrders[counter] = months.getCount();
					counter++;
				}
				
				
				req.setAttribute("monthOrders", monthOrders);
				req.setAttribute("currentyear", currentyear);
		
				
				// Get the ArrayList of Months for this year with their number of orders
				List<Orders30Days> listOrders30Days = adminService.getOrdersTotal30Days();
		
				
				for(Orders30Days days: listOrders30Days){
					Date d = days.getDate();
					String strDay = d.toString();
					strDay = strDay.substring(8,10) + "/" + strDay.substring(5,7);
					days.setStrDate(strDay);
					counter++;
				}
				
				
				req.setAttribute("listOrders30Days", listOrders30Days);
		
				
				// Get the OrdersByLevel object (level&count) of orders for each of the possible users' levels
				OrdersByLevel bronzeOrdersCount = adminService.getOrdersByLevel("Bronze");
				OrdersByLevel silverOrdersCount = adminService.getOrdersByLevel("Silver");
				OrdersByLevel goldOrdersCount = adminService.getOrdersByLevel("Gold");
				
						
				req.setAttribute("bronzeOrdersCount", bronzeOrdersCount);
				req.setAttribute("silverOrdersCount", silverOrdersCount);
				req.setAttribute("goldOrdersCount", goldOrdersCount);
				
				
				return "admin/adminHome";
			
			// User logged in, not an administrator so go back to viewer's page
			} else {
				
				return "viewer/home";
			}
			
		// No one logged in go back to viewer's home page
		} else {
			
			return "viewer/home";
		}
        
    }
	
	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the orders 
	 * that are awaiting approval as a list which it sets as a request attribute and it instructs the controller to open the page to 
	 * display the appropriate orders.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goApproveOrders")
    public String approveOrders(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				List<Orders> listApproveOrders = adminService.getOrdersForApproval();
				req.setAttribute("listApproveOrders", listApproveOrders);
		    	return "admin/adminOrderApprove";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }	
	
	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the orders 
	 * that are awaiting delivery as a list which it sets as a request attribute and it instructs the controller to open the page to 
	 * display the appropriate orders. 
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goDeliverOrders")
    public String deliverOrders(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				List<Orders> listDeliverOrders = adminService.getOrdersForDelivery();
				req.setAttribute("listDeliverOrders", listDeliverOrders);
		    	return "admin/adminOrderDeliver";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }

	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the orders 
	 * that can be deleted as a list which it sets as a request attribute and it instructs the controller to open the page to display the 
	 * appropriate orders. 
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goDeleteOrders")
    public String deleteOrders(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				List<Orders> listDeleteOrders = adminService.getOrdersDeletion();
				req.setAttribute("listDeleteOrders", listDeleteOrders);
		    	return "admin/adminOrderDelete";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }
	
	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the users 
	 * to be approved as a list which it sets as a request attribute and it instructs the controller to open the page to display the 
	 * appropriate orders. 
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goApproveUsers")
    public String approveUsers(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				List<User> listApproveUsers = adminService.getUsersApprove();
				req.setAttribute("listApproveUsers", listApproveUsers);
		    	return "admin/adminUserApprove";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }

	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the  
	 * balance increases to be approved as a list which it sets as a request attribute and it instructs the controller to open the page 
	 * to display the appropriate orders. 
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goApproveIncreaseBalance")
    public String approveIncreaseBalance(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				List<IncreaseBalance> listIncreaseBalances = adminService.getIncreaseBalances();
				req.setAttribute("listIncreaseBalances", listIncreaseBalances);
		    	return "admin/adminIncreaseBalanceApprove";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }

	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the  
	 * order's id that has to be approved from a request parameter and sets it as approved.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */	
	@RequestMapping("goOrderApproval")
    public String orderApproval(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				int orderId = Integer.parseInt(req.getParameter("orderId"));
				adminService.orderSetApproved(orderId);
				
				return "redirect:/admin/goApproveOrders";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }


	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the  
	 * order's id that is to be set from a request parameter and sets it as delivered.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */	
	@RequestMapping("goOrderDelivery")
    public String orderDelivery(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				int orderId = Integer.parseInt(req.getParameter("orderId"));
				adminService.orderSetDelivered(orderId);
				
				adminService.checkUserLevel(orderId);
				
				return "redirect:/admin/goDeliverOrders";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }

	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the  
	 * order's id that is to be deleted and sets it deletes it.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */	

	@RequestMapping("goOrderDelete")
    public String orderDelete(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				int orderId = Integer.parseInt(req.getParameter("orderId"));
				adminService.orderSetDeleted(orderId);
				
				return "redirect:/admin/goDeleteOrders";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}
		
    }
	
	
	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the  
	 * user's id that is to be set from a request parameter and sets it as approved.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */	

	@RequestMapping("goUserApproval")
	public String userApproval(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				int userId = Integer.parseInt(req.getParameter("userId"));
				adminService.userSetApproved(userId);
				
				return "redirect:/admin/goApproveUsers";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}

	}


	/**
	 * Firstly this method checks that there is someone logged in, if not it opens the viewer's home page. It then checks if the person 
	 * logged in is an ADMIN, if not it opens the viewer's home page. After confirming that the ADMIN is logged in, it retrieves the  
	 * user's id and the increase amount from request parameters and and it increase's the user's balance.
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */	

	@RequestMapping("goIncreaseBalanceApproval")
	public String increaseBalanceApproval(HttpSession session, HttpServletRequest req) {

		// Need to check if someone is logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
					
			// Make sure that it is an Administrator that is logged in.
			if(o.getAccesslevel().compareTo("Administrator") == 0) {

				// Take in the userId and the increase parameters
				int userId = Integer.parseInt(req.getParameter("userId"));
				int increaseId = Integer.parseInt(req.getParameter("increaseId"));
				
				// Call a method to set the increase to approved and to increase the user's balance and to create a log
				adminService.increaseBalanceSetApproved(userId, increaseId);
				
				return "redirect:/admin/goApproveIncreaseBalance";
				
			// User logged in, not an administrator so go back to viewer's page
			} else {
					
				return "viewer/home";
			}
				
		// No one logged in go back to viewer's home page
		} else {
				
			return "viewer/home";
		}

	}
	
	
	/**
	 * This is the method used to searcg for particular games by their names. The method retrieves the platform and the game name from the 
	 * request parameter that is passed in. The method then searches for the games in the database. After retrieving the games the method 
	 * then finds the appropriate game cover puts the game and it's cover into an arraylist that it sets as a request attribute along with 
	 * the game name and platform. It then checks if a User is logged in when this search occurs, if there is a user logged in then it 
	 * instructs the controller to open a the appropriate user page (gold, silver bronze) with the results and if not it instructs the 
	 * controller to open the viewer's search page.      
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goSearchGames")
	public String searchGames(HttpSession session, HttpServletRequest req) {

		// Get the platform parameter of what is to be searched
		int plat = Integer.parseInt(req.getParameter("platform"));
		
		// Get the Name that is to be searched (If blank then make it an empty string)
		String searchName = (req.getParameter("name")==null)?"":req.getParameter("name");
		
		// Call a method to get an Arraylist of the searched games
		List<Game> foundGames = adminService.doSearch(plat, searchName);
				
		// Create an ArrayList of GameDisplay objects (Game and Covers/screens)
		ArrayList<GameDisplay> searchedGames = new ArrayList<GameDisplay>();
			
		// Load the games found into the searchedGames list
		for(Game g: foundGames) {
			GameDisplay gameD = new GameDisplay();
				
			// For this Game in the list, get the small game cover from the database using a method called getGameCover and
			// Save the cover with the game into the searchedGames list
			gameD.setSmallCoverLoc((adminService.getGameCover(g.getId(), "Medium")));
			gameD.setGame(g);
			
			// Add this GameDisplay object to the searchedGames List 
			searchedGames.add(gameD);
		}

		
		// Set request attributes to hold the Arraylist of the search
		req.setAttribute("listGames", searchedGames);
		req.setAttribute("name", searchName);
		req.setAttribute("platform", plat);
			
			
		User userObj = (User) session.getAttribute("userObj");
		
		// Find out which search results page to display and open it 
		if (userObj == null) {
			return "viewer/viewerResults";
			
		} else {
			
			// Retrieve the userHome session variable
			String userHome = (String) session.getAttribute("userHome");
			
			// Find the User's results page to display based on their home page 
			if (userHome.compareTo("admin/user_gold_home") == 0){
				return "admin/userGoldResults";
			
			} else if(userHome.compareTo("admin/user_silver_home") == 0){
				return "admin/userSilverResults";
			
			} else {
				return "admin/userBronzeResults";
			}
			
		}

	}

	
	/**
	 * This method places an order for a User. The user id and the game id are passed in as parameters. The method retrieves these 
	 * parameters and saves the order for the user. The method then instructs the controller to open the user's home page based on the 
	 * session attribute userHome that was set when the user logged in.   
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goOrderGame")
    public String orderGame(HttpSession session, HttpServletRequest req) {

		// Get the User and the GameId
		int userId =Integer.parseInt((String) req.getParameter("userId")); 
		int gameId = Integer.parseInt((String) req.getParameter("gameId"));
		
		adminService.saveOrder(userId, gameId);
		
		// Decide on the user's home page based on the user's level and set it as a session variable.		
		return (String) session.getAttribute("userHome");
		
    }
	
	
	/**
	 * This method is called when an ADMIN clicks on the logout link. It removes the userObj and userHome attributes from the session and 
	 * redirects the user back to the startHome dummy html page as if the application was just loading for the first time.
	 * @param session The current session
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goLogOut")
    public String logout(HttpSession session) {

		session.removeAttribute("userObj");
		session.removeAttribute("userHome");
		
		return "redirect:/startHome";
    }

	
	/**
	 * This method runs when a User clicks the Increase Balance link on a Gold user's page. The method instructs the controller to open 
	 * the gold user's increase balance page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goGoldIncreaseBalPage")
	public String GoldIncreaseBalPage() {
		return "admin/userGoldIncreaseBal";
	}
	

	/**
	 * This method runs when a User clicks the Home link on a Gold user's page. The method instructs the controller to open 
	 * the gold user's home page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goGoldHomePage")
	public String GoldHomePage() {
		return "admin/user_gold_home";
	}


	/**
	 * This method runs when a User clicks the Increase Balance link on a Silver user's page. The method instructs the controller to open 
	 * the silver user's increase balance page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goSilverIncreaseBalPage")
	public String SilverIncreaseBalPage() {
		return "admin/userSilverIncreaseBal";
	}
	

	/**
	 * This method runs when a User clicks the Home link on a Silver user's page. The method instructs the controller to open 
	 * the silver user's home page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goSilverHomePage")
	public String SilverHomePage() {
		return "admin/user_silver_home";
	}	


	/**
	 * This method runs when a User clicks the Increase Balance link on a Bronze user's page. The method instructs the controller to open 
	 * the bronze user's increase balance page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goBronzeIncreaseBalPage")
	public String BronzeIncreaseBalPage() {
		return "admin/userBronzeIncreaseBal";
	}
	

	/**
	 * This method runs when a User clicks the Home link on a Bronze user's page. The method instructs the controller to open 
	 * the bronze user's home page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goBronzeHomePage")
	public String BronzeHomePage() {
		return "admin/user_bronze_home";
	}
	

	/**
	 * This method is called when a logged in ADMIN user clicks the User History link. The method retrieves all of the approved users' 
	 * e-mails and puts them in a list which it sets as a request attribute. It then instructs the controller to open the adminChooseUser 
	 * page which will allow the user to select the user whose history is to viewed.
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goUsersHistory")
	public String AdminPickUserHistory(HttpServletRequest req) {
		
		List<User> listUsers = adminService.getApprovedEmails();
		
		req.setAttribute("listUsers", listUsers);
		
		return "admin/adminChooseUser";
	}

	
	/**
	 * Method called when a logged in User clicks on the Increase Balance link. The method first verifies that a User is logged in and if 
	 * not then it opens the viewer's home page. It then saves the increase request from the user and instructs the controller to open  
	 * the user's home page.   
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goIncreaseBalance")
	public String saveIncreaseBalance(HttpSession session, HttpServletRequest req) {

		User userObj = (User) session.getAttribute("userObj");
		
		// If User not logged in the open the viewer's page
		if (userObj == null) {
			return "viewer/home";
			
		} else {
			
			// Retrieve the increase that is requested
			String inc = req.getParameter("increase");
			float incValue = Float.parseFloat(inc);
			
			// User logged in so add increase request to the increasebalance table
			IncreaseBalance increase = new IncreaseBalance();
			
			increase.setUserId(userObj.getId());
			increase.setIncrease(incValue);
			increase.setApproved("No");
			increase.setDate(new Date());
			
			adminService.saveIncreaseBalance(increase);
			
			// Retrieve the userHome session variable
			String userHome = (String) session.getAttribute("userHome");
			
			// Return the User to their home page 
			return userHome;
					
		}
	
	}
	
	
	/**
	 * This method retrieves the current user from the session object. If no user is logged in then the application is re-directed to the 
	 * viewer's home page. Based on the user's level (Gold, Silver, Bronze) the appropriate change details page is loaded.
	 * @param session The current session
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goChangeDetails")
	public String ChangeDetails(HttpSession session) {

		User userObj = (User) session.getAttribute("userObj");
		
		// Make Sure a user is logged on 
		if (userObj == null) {
			return "viewer/home";
			
		} else {
		
			// Find the user's userHome and open the appropriate Change Details page
			String userHome = (String) session.getAttribute("userHome");
			
			if (userHome.compareTo("admin/user_gold_home") == 0){
				return "admin/userGoldChangeDetails";
			
			} else if(userHome.compareTo("admin/user_silver_home") == 0){
				return "admin/userSilverChangeDetails";
			
			} else {
				return "admin/userBronzeChangeDetails";
			} 
			
		}
	}
	
	
	/**
	 * When a user changes their details and clicks on the update button, this method is called. It retrieves the user from a session 
	 * attribute and if a valid user is logged on then it saves the details and instructs the controller to open the user's home page 
	 * which it also retrieves from a session attribute.     
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goSaveChangeDetails")
	public String saveChangeDetails(HttpSession session, HttpServletRequest req) {
		
		// Retrieve the user and the user's userHome 
		User userObj = (User) session.getAttribute("userObj");
		String userHome = (String) session.getAttribute("userHome");
		
		
		// Make Sure a user is logged on 
		if (userObj == null) {
			return "viewer/home";
					
		} else {
	
			// Load the user with the new data
			userObj.setTitle((String) req.getParameter("title"));
			userObj.setForename((String) req.getParameter("forename"));
			userObj.setSurname((String) req.getParameter("surname"));
			userObj.setHomephone((String) req.getParameter("homephone"));
			userObj.setMobile((String) req.getParameter("mobile"));
			userObj.setAddress((String) req.getParameter("address"));
						
			// Re-Save the Session variable userObj with the new details
			session.setAttribute("userObj", userObj);
			
			adminService.updateUser(userObj);
			
			return userHome;
		}
	}
	
	
	/**
	 * This is the method that retrieves the history of a user based on an ADMIN's selection of a user whose history they want to view or 
	 * from a user's selection of the Account History link when they are logged in. The method first checks that someone is logged in, if 
	 * they are not then the viewer's home page is displayed. 
	 * If an ADMIN is logged in then the selected user's id and e-mail are retrieved from request parameters and the user's history is 
	 * retrieved from the database and stored in a list. the List and the user's e-mail are stored as request attributes and the 
	 * adminChooseUser page is loaded again to display the history.
	 * If a user is logged in then a List of the user's history is retrieved from the database and set as a request attribute and then 
	 * based on the user's level (Gold, Silver, Bronze), the appropriate page is loaded.   
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goAccountHistory")
	public String accountHistory(HttpSession session, HttpServletRequest req) {
		
		// Retrieve the user and the user's userHome 
		User userObj = (User) session.getAttribute("userObj");
		
		// Make Sure a user is logged on 
		if (userObj == null) {
			return "viewer/home";
							
		} else {
			
			List<Logs> listLogs = null;
		
			// If the User's level is an administrator
			if(userObj.getAccesslevel().compareTo("Administrator")==0){
				
				String u = req.getParameter("userId");
				int userId = Integer.parseInt(u);
				
				// Get the User's e-mail
				String email = adminService.getUserEmail(userId);
				
				// Get an ArrayList of all the User's records from Logs table
				listLogs = adminService.getUserLogs(userId);

				// Set the list and selected option as request attributes
				req.setAttribute("listLogs", listLogs);
				req.setAttribute("account", email);
			
				// Return to the adminHome
				return "admin/adminChooseUser";

				
			} else {
			
				// Get an ArrayList of all the User's records from Logs table
				listLogs = adminService.getUserLogs(userObj.getId());

				// Set the list as a request attribute
				req.setAttribute("listLogs", listLogs);
			
				// Decide which page to return the user to
				if(userObj.getLevel().compareTo("Gold")==0){
					return "admin/userGoldHistory";
				
				}else if(userObj.getLevel().compareTo("Silver")==0) {
					return "admin/userSilverHistory";
				
				} else {
					return "admin/userBronzeHistory";
				}
				
			}
		}
				
	}

	
	/**
	 * If a user clicks on a game to view the details of that game then this method is called. The method first checkjs that someone is 
	 * logged in. It retrieves the user's home page and id and also the clicked on game id from the session attributes and request 
	 * parameters. It then adds a view record to the views table in the database. It then retrieves the Game details, the Game Covers 
	 * and the Game Screens from the database and sets them as request attributes. It instructs the controller to open the user's game 
	 * details page based on the level of the User (Gold, Silver, Bronze) which is asertained by the User's home page.      
	 * @param session The current session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goUserViewGame")
	public String goViewGame(HttpSession session, HttpServletRequest req) {
		
		// Check if someone is logged on in this session
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
			
			// Retrieve the userHome session variable
			String userHome = (String) session.getAttribute("userHome");
						
			int userId = o.getId();
		
		
			int gameId = Integer.parseInt(req.getParameter("gameId"));
			
			
			// Add a view to the views table for this game
			adminService.addView(gameId, userId);
			
			
			// Get the game info from the game table
			Game game = adminService.getGame(gameId);
			
			// Get the large cover from the gamecover table
			String cover = adminService.getGameCover(gameId, "Max");
			
			// Get the game screenshots from the gamescreens table
			List<GameScreens> gameScreens = adminService.getGameScreens(gameId);
			
			// Set the game, cover and screenshots as request variables
			req.setAttribute("game", game);
			req.setAttribute("cover", cover);
			req.setAttribute("gameScreens", gameScreens);
			
						
			// Find the User's game details page to display based on their home page 
			if (userHome.compareTo("admin/user_gold_home") == 0){
			
				return "admin/userGoldGameDetails";
						
			} else if(userHome.compareTo("admin/user_silver_home") == 0){
				
				return "admin/userSilverGameDetails";
						
			} else {
				
				return "admin/userBronzeGameDetails";
			}
			
		} else{

			return "viewer/home";
		}
	}
	
}
