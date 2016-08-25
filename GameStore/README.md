# Shop
GameStore Web Application

This was a college project that myself and a colleague Alan Brady wrote while doing a Higher Diploma in Web Development course in Griffith 
College.

My colleague designed and wrote the web pages (using Dreamweaver & photoshop) and I did all of the programming, database, and JSP work on the 
web pages.

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



??????????????I also included a "Screenshots And Printout.zip" zip file containing the screenshots from the application.

# Functionality

  Retail Orders
    
      The ability to add Retail Orders
      
      Apply discounts
      Gross Profit percent and value calculated and displayed on screen
      Add customer details to the order
      Choose order pay type
      Print receipts
    
  Trade orders
  
      The ability to add Trade orders
      Gross Profit percent and value calculated and siplayed on screen
      Add tradesmen details to the order
      Choose order pay type
      print receipts details
      
  Suspended Orders
  
      Add retail or trade suspended orders
      
      List of Suspended Orders Displayed, can filter Retail or Trade Suspended Orders
      Enter a Payment against a Suspended Order
      Select a Suspended Order To Process
          Release Order Lines from the Suspended Order
          Add new Lines to the Suspended Order and re-print
          Remove Lines from a Suspended Order
  
  Quotations
  
      Add Retail Quotes similar to orders without payment type
      Add Trade Quotes similar to orders without payment type
      Convert a quote into an order
          List of Quotations displayed with many filters to find quotation
              Type, No, Name, Address, Date Range, Phone Value, Item Code or Description of item on quote
          On Selection lines on quote displayed, convert button to open retail/trade order screen quote
  
  Accounts
  
      List of all accounts displayed: name, address, phone, balance
      Can filter by above fields
      On selection of account - detail screen loaded with name, balance and last 20 transactions
          A statement can be displayed for the account for a specific ate range
          A payment can be made against the account
          An trade order can be entered for the account
          A trade quote can be entered for the account
          A list of account Quotes can be displayed and converted to orders if required
  
  Refunds
  
      List of Refunds displayed with many filters to find an order to refund against
          Type, No, Name, Address, Date Range, Phone Value, Item Code or Description of item on quote
      On Selection lines on quote displayed broken down to a quantity of 1 on each line
          User selects the lines to refund which opens a frame showing to be refunded items with values
          Refund button on to be refunded performs the refund.
  
  Product maintenance
  
      The list of Products are displayed - filters available are code and description
          User can perform CRUD operations on Products
  
  Customer maintenance
  
      The list of Customers are displayed - filters available are name, address and phone
          User can perform CRUD operations on Customers
  
  Tradesmen Maintenance
  
      The list of Tradesmen are displayed - filters available are name, address and phone
          User can perform CRUD operations on Tradesmen
      
  Settings
  
      Various Vat and Receipt printing settings
          Choose whether or not to print icons on the top of in the body of the page with their X an Y positions
          Choose whether or not to print vertical grid lines on a receipt
          Enter various lines of footer text that can be applied to the receipt's footer
          Enter the current Vat Rate
