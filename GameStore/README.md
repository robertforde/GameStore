# Shop
GameStore Web Application

This was a college project that myself and a colleague Alan Brady wrote while doing a Higher Diploma in Web Development course in Griffith 
College.

My colleague designed and wrote the web pages (using Dreamweaver & photoshop) and I did all of the programming, database, and JSP/graph work on 
the web pages.

The project is based on a game store that uses a membership based system, whereby the user becomes a member, gives credit card details and can 
increase there balance at the store and order games from the store's website. The incentive to use the store is that members can gain bronze, 
silver or gold membership level with the higher their order value and this grants them better discounts on all of the games that they purchase.

#Installation
Download source code and in a JEE version of Eclipse File Menu->Import->General->ExistingProjects into Workspace

Browse to the directory where you downloaded to and click finish

You need a MySql Database - there is a gamestore.sql sql dump file which can be imported into your MySql and this will create the database and 
necessary tables as well as populating it with various dummy data that I created. I also put a logins.txt text file in the project with users 
and their passwords that are in the database.

You will need to change the username and password in the src/main/webapp/WEB-INF/jdbc.propertiesspring file to your MySql username and password.

You will also need to setup a Web Server in the project, the one I used was Tomcat 7.

To run the project right click on the project and select Run As -> Run on Server with either MySql open or even just MySqld.exe running

Because a lot of the application is dependant on when orders have been entered, like tje charts. To see the application working properly you 
would need to set your system date to 25/05/2015 as most of the data was entered around this time

# Functionality

  Viewers
    
      Presented with the top 5 most viewed games
      
      Can Search for Games by Name and platform
      
      Clicking into a Game displays a large cover, prices, screenshots and description
      
      Home - Reload the initial home page
      
      Playstation 3 - Display the most viewed Playstation 3 games
      
      Playstation 4 - Display the most viewed Playstation 4 games
      
      Xbox One - Display the most viewed Xbox One games
      
      Xbox 360 - Display the most viewed Xbox 360 games
      
      WII - Display the most viewed WII games
      
      Nintendo - Display the most viewed Nintendo games
      
      Pc Games - Display the most viewed Pc games
      
      Contact Us - Displays contat details
      
      Registration - Allows viewers to Register
      
      Log In - Allows users to log in
      
 Users
      
      An unsuccessful login shows an error page
      
      A successful login showsthe User's Home page
      
      Home - The user's membership card with name and balance is displayed along with a search panel to search 
      for games by name and platform
      
      Increase Balance - This is where the user requests an increase in their balance from their credit card
      
      Change Details - This page allows the user to change their personal details
      
      Account History - This shows the users transactions (date, status, value, game and balance at the time) 
      for every order they placed
      
      Log out - Logs the user out of their account and back to the viewer's home page  
    
 Admin
  
      On initial login the admin user is presented with a 3d pie chart of amount of the top five delivered games 
      of all platforms divided into their portion of the total delivered games of the top 5 delivered games. The 
      admin user also gets a a 3d bar chart of the year's orders broken into the months of the year. There is 
      also a line graph showing the total order qty ovr the last 30 days. Finally there is another 3d bar chart 
      showing the breakdown or orders per user level (Gold, Silver, Bronze).
      
      At any time and on any of the admin screens the user can run the charts against a particular platform or 
      all platforms via a dropdown on the screen
      
	  Delete Ord - A list of all of the orders that can be deleted (Ordered or Approved) are presented on screen 
	  with a trash can for easy deletion.
	  
	  Approve Ord - A list of all of the orders that are awaiting approval (Ordered) are presented on screen 
	  with a thunbs up sign for easy approval.
	  
	  Deliver Ord - A list of all of the orders that are to be delivered (Approved) are presented on screen with 
	  a van sign for easy delivery. 

	  Users - Approve Users - A list of all of the users that are awaiting approval (resistered not approved) 
	  are presented on screen with a thunbs up sign for easy approval.
	  
	  Users - Approve Increase Balance - A list of all of the balance increase requests are presented on screen 
	  with a thunbs up sign for easy approval.
	  
	  User History - This loads a page where the admin user can select any user from the database using a 
	  dropdown. When a user is submitted a page then loads containing all of that user's transactions (date, 
	  status, value, game and balance at the time) for every order they placed. 