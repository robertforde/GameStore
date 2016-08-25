package com.gamestore.controller;

import java.util.ArrayList;
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
import com.gamestore.model.Platform;
import com.gamestore.model.User;
import com.gamestore.model.ViewedGames;
import com.gamestore.services.AdminService;
import com.gamestore.services.ViewerService;


/**
 * This is the controller class that handles all of the operations when a User is logged in.
 * @author Robert Forde
 *
 */
@Controller
public class ViewerController {

	@Autowired
	private AdminService adminService;
		
	@Autowired
	private ViewerService viewerService;
	
	
	/**
	 * This is the method that will run first when the application loads.
	 * The method first checks that someone is logged in, if there is then it opens the appropriate home page. If there is not then it will 
	 * retrieve a List of platforms from the database. For each of these platforms it will then find the 5 games with the most views in the
	 * last month. It also gets the medium game covers of each of the top 5 games and then it stores the List as a request attribute and it
	 * opens the viewer's home page. 
	 * @param session The current Session
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("startHome")
    public String homeStart(HttpSession session) {

	    // Need to check if someone is already logged on in this session.
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
			//Find out if it is a User or an Admin and display the appropriate page.
			if(o.getAccesslevel()=="Admin") {
				return "redirect:/admin/goAdminHome2";
			}else if(o.getAccesslevel()=="User") {
				return "redirect:/admin/goUserFindHome";
			}else {
				// If this person is not an admin or a user but they have a session.
				return "viewer/home";	
			}
		}
		else {
			
			// No session so we need to create ArrayLists of the top 5 viewed games (this month) and put them into the "viewer's" session. 
			// This will happen once per session so there is no extra load on the database.
			
			String listName = "";
			List<Platform> platforms = viewerService.getPlatforms();
			
			
			// Loop to put arraylists of the top 5 viewed games per platform with their covers into this viewers session object.
			for(Platform p:platforms){
				listName = "Views" + p.getName();
				List<ViewedGames> topGames = viewerService.getViewsTopMonthlyGames(5, p.getId());
				topGames = viewerService.getGamesCovers(topGames, "Medium");
				session.setAttribute(listName, topGames);
			}
		
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
		List<Game> foundGames = viewerService.doSearch(plat, searchName);
				
		// Create an ArrayList of GameDisplay objects (Game and Covers/screens)
		ArrayList<GameDisplay> searchedGames = new ArrayList<GameDisplay>();
			
		// Load the games found into the searchedGames list
		for(Game g: foundGames) {
			GameDisplay gameD = new GameDisplay();
				
			// For this Game in the list, get the small game cover from the database using a method called getGameCover and
			// Save the cover with the game into the searchedGames list
			gameD.setSmallCoverLoc((viewerService.getGameCover(g.getId(), "Medium")));
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
	 * This method runs when a user clicks the Registration link on the viewer's page. The method instructs the controller to open 
	 * the registration page.
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goRegistration")
	public String goReg() {
		return "viewer/registration";
	}
	

	/**
	 * This method runs when a user clicks the Log In link on the viewer's page. The method instructs the controller to open 
	 * the login page.
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goLoginPage")
	public String goLoginPage() {
		return "viewer/login";
	}
	

	/**
	 * This method runs when a user clicks the Corporate link on the viewer's page. The method instructs the controller to open 
	 * the corporate page.
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goCorporatePage")
	public String goCorporatePage() {
		return "viewer/corporate";
	}
	
	
	/**
	 * This method runs when a user clicks the Membership link on the viewer's page. The method instructs the controller to open 
	 * the membership page.
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goMembership")
	public String goMembership() {
		return "viewer/membership";
	}
	
	
	/**
	 * This method runs when a user clicks the Contact Us link on the viewer's page. The method instructs the controller to open 
	 * the contactUs page.
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goContact")
	public String goContact() {
		return "viewer/contactUs";
	}

	
	/**
	 * This method runs when a User enters their e-mail and password on the login page and submits them. The method checks if the user with 
	 * this password exists in the database. If this user password doesn't exist then the user is presented with an error login page. The 
	 * method then checks if the user in the database has been approved and if they have not, the user is presented with an un-approved 
	 * page. It saves the User in a session attribute. It then checks if it is an administrator logging on, if it is then the method 
	 * re-directs you to the admin home page. If it is a user then it calls a method to determine which home page is the correct one and 
	 * then re-directs the user to their home page. 
	 * @param session The current session
	 * @param email The e-mail request parameter
	 * @param password The password request parameter
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
	 * This method runs when a user clicks the Log Out Us link on the User or Admin pages. The method removes the userObj (User) and the 
	 * userHome (Home Page) session attributes and instructs the controller to open the viewer's home page.
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goLogOut")
    public String logout(HttpSession session) {

		session.removeAttribute("userObj");
		session.removeAttribute("userHome");
		
		return "viewer/home";
		
    }
	
	
	/**
	 * This method runs when a user clicks the Playstation 3 link on the viewer's home page. The method instructs the controller to open
	 * viewer's playstation 3 page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goPlaystation3")
    public String goPlaystation3() {

		return "viewer/playstation3";
		
    }

	
	/**
	 * This method runs when a user clicks the Playstation 4 link on the viewer's home page. The method instructs the controller to open
	 * viewer's playstation 4 page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goPlaystation4")
    public String goPlaystation4() {

		return "viewer/playstation4";
		
    }

	
	/**
	 * This method runs when a user clicks the xbox one link on the viewer's home page. The method instructs the controller to open
	 * viewer's xbox one page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goXboxOne")
    public String goXboxOne() {

		return "viewer/xboxOne";
		
    }

	
	/**
	 * This method runs when a user clicks the xbox 360 link on the viewer's home page. The method instructs the controller to open
	 * viewer's xbox 360 page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goXbox360")
    public String goXbox360() {

		return "viewer/xbox360";
		
    }

	
	/**
	 * This method runs when a user clicks the wii link on the viewer's home page. The method instructs the controller to open
	 * viewer's wii page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goWii")
    public String goWii() {

		return "viewer/wii";
		
    }

	
	/**
	 * This method runs when a user clicks the nintendo link on the viewer's home page. The method instructs the controller to open
	 * viewer's nintendo page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goNintendo")
    public String go3ds() {

		return "viewer/nintendo";
		
    }

	
	/**
	 * This method runs when a user clicks the pc link on the viewer's home page. The method instructs the controller to open
	 * viewer's pc page. 
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goPc")
    public String goPc() {

		return "viewer/pc";
		
    }

	
	/**
	 * This method is called when a person submits registration details. It creates a new User and saves it to the database
	 * @param email The e-mail that was entered
	 * @param password The password that was entered
	 * @param title The title that was entered
	 * @param forename The forename that was entered
	 * @param surname The surname that was entered
	 * @param homePhone The home phone number that was entered
	 * @param mobile The mobile phone number that was entered
	 * @param address The address that was entered
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goRegister")
    public String registerUser(@RequestParam("email") String email, @RequestParam("password") String password,
    		@RequestParam("title") String title, @RequestParam("forename") String forename,
    		@RequestParam("surname") String surname, @RequestParam("homePhone") String homePhone,
    		@RequestParam("mobile") String mobile, @RequestParam("address") String address) {

		User user = new User();
		user.setPassword(adminService.encrypt(password));
		user.setApproved("No");
		user.setLevel("Bronze");
		user.setTitle(title);
		user.setForename(forename);
		user.setSurname(surname);
		user.setEmail(email);
		user.setAccesslevel("User");
		user.setHomephone(homePhone);
		user.setMobile(mobile);
		user.setAddress(address);
		user.setBalance(0);
		
		
		adminService.registerUser(user);
		
		return "viewer/registered";
		
    }
	
	
	/**
	 * This method is called when a viewer views the details of a game. The method retrieves the game id from the request parameter and 
	 * adds a view to the views table in the database. It then retrieves the games details, game covers and game screens from the database. 
	 * The method then sets the game details, the game cover and the game screens as request attributes and instructs the controller to 
	 * open the gameDetails page.    
	 * @param session The current Session
	 * @param req The Request Parameters passed
	 * @return A String representing the path and jsp page that has to opened next
	 */
	@RequestMapping("goViewGame")
	public String goViewGame(HttpSession session, HttpServletRequest req) {
		
		int userId = 1;
		
		// Check if someone is logged on in this session and if not leave userId as 1 (admin i.e a viewer)
		User o = (User) session.getAttribute("userObj");
		if(o!=null){
			userId = o.getId();
		}
		
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
		
		// Return the user to the view game screen 
		return "viewer/gameDetails";
	}

}
