<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<title>ALRO GAMES  CORPOTATE</title>

	<link href="<c:url value='/resources/css/styles.css' />" rel="stylesheet" />

</head>

<body style="margin:0; padding:0">


<!--Wrapper this is the start of the wrapper Wrapper is width 1000px and height is auto, it will contain the following header, log navbar, main and footer. which will be displayed below.-->
<div id="wrapper">

<!--Header-->

<div id="header">

<!--Logo alro-games-->

<div id="logo"><img src="<c:url value='/resources/images/LOGO.jpg' />" width="261" height="100" border="0" />
  
</div>


<!--End of alro-games-->


</div>

<!--End Header-->

<jsp:include page="viewerNav.jsp" />

<!--Main--------------------------------------------------------------------------------------------------------------------------->
<!--generally contains all stuff with in the page, this concludes banners, display boxes of verious offers and softwate releases.-->

<div id="main">

<!--banner-->

<div id="banner2"></div>

<jsp:include page="viewerSearchForm.jsp" />
        
<div id="box2">
  <div class="title"> Corporate Profile  </div>
  <div id="boxcontaints">
    <div class="business_image"><img src="<c:url value='/resources/images/alroworkers.jpg' />" alt="ALRO WORKER" width="300" height="241" /></div>
    <span id="boxcontaints">ALRO Games is the world's largest multichannel video game retailer. ALRO Game&rsquo;s retail network and family of brands include 6,488 company-operated stores in 15 countries worldwide and online at <a href="http://www.alro-games.ie">www.alro-games.com</a>
    </span>
    <p>Our network also includes: www.alrogate.com, a leading browser-based game site; Game Informer(R) magazine, the leading multi-platform video game publication; Spawn Labs, a streaming technology company; and a digital PC game distribution platform available at <a href="http://www.gamestop.com/PCgames">www.alro-games.com/PCgames</a>.</p>
    <p>General information on Alro Games Corp. can be obtained at the company's corporate website. Follow Alro Games on Twitter @<a href="https://twitter.com/GameStop">www.twitter.com/alrogames</a> and find Alro Games on Facebook @ <a href="https://www.facebook.com/alrogames">www.facebook.com/alrogames</a>.</p>
    <p>Our principal executive offices are located at Block W, East Point Business Park, Dublin 3 ,Our telephone number is 1-800 817 333 (Call free)</p>
    <div id="profile1">
      <div class="picture">
        <div class="picturename"><strong>Osama Abushama</strong></div>
      
      
      </div>
      <div class="bio"><strong>President and Chief Executive Officer </strong><br />
        <br />
Over the course of an impressive 33-year career,Osama Abushama  has made significant contributions to AlRO Games and the entire gaming industry. As the company&rsquo;s president and chief executive officer,Osama  has overall management responsibility for the Pearson Alro Games Company. <br />
<br />
Through successful communication of a clear vision and a team-based collaborative approach, he has seen the company grow to become the global leader in computer-based assessments. In recognition of these efforts, Osama received the 2011 OLG Award for Professional Contributions and Service to Gaming from the Association of online gaming. </div>
    </div>
    
     <div id="profile1">
      <div class="picture1">
      <div class="picturename"><strong>Robert Forde</strong></div>
      </div>
      <div class="bio"><strong>Product Manager and java / php developer</strong><br />
        <br />
        Robert Forde is currently the   product manager and java / PHP engineering at AlRO Games since 2012. Prior to Alro games , he spent 10 years at Sun Microsystems, working on Java, J2EE, Enterprise Architecture and Web Services. He was Sun's Lead Architect for eBay's V3 project to build their next generation auction platform based on Java. <br />
        He worked with many premier enterprises as a consulting architect to help design and implement highly scalable mission critical applications. Forde is recognized for his contribution to the Java Community as the co-author of the widely adopted industry standard book on J2EE patterns, best practices and refactorings - <em>Core J2EE</em> <em>Patterns: Best Practices and Design Strategies</em>, published by Prentice Hall. follow him on Twitter at @RobertForde.</div>
    </div>
    
     <div id="profile1">
      <div class="picture2">
      <div class="picturename"><strong>Alan Brady</strong></div>
      
      </div>
      <div class="bio"><strong>Design Manager</strong><br />
        <p>Professionally, Alan t has been developing websites since 2001. Now he manages a team of developers, designers, and project managers, a support team, <em>and</em> a managed services team. As the Web Director at ALFO Games, Alan also manages the creation of graphics design banners used in the Alro Games website.</p>
        <p>&nbsp;</p>
         follow him on Twitter at @alanbrady.</div>
    </div>
  
  </div>
</div>
</div>
<!--End of Main-------------------------------------------------------------------------------------------------------------------->

</div>

<jsp:include page="viewerFooter.jsp" />


</body>
</html>