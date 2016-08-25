<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<title>ALRO GAMES MEMBERSHIP</title>

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

<div id="banner4"></div>

<jsp:include page="viewerSearchForm.jsp" />

<div id="box2">
  <div class="title">MEMBERSHIP</div>
  <div id="boxcontaints">
   <strong>Free Membership</strong><br />
      <br />
      It's FREE to join. All you have to do is sign up with your email address at your local ALRO GAMES and you'll get a membership card so you can immediately start scoring points. As a member, you will also get invitations to member-only events, advance notice of sales, promotions, and other offers.
      
      HOW TO SCORE POINTS: Show your card whenever you make a purchase or trade and you'll score points for each game or system you trade in, you'll score points for any new and previously-owned games, consoles, accessories you buy and more! Accumulate and spend your points all year long. <br />
      <br />
      Then, choose from a variety of rewards from the ALRO GAMES rewards catalog. Cool, new and exclusive stuff is added weekly!
      
      HOW TO GET REWARDS: Go online any time to check out the ALRO GAMES Rewards catalog, or we'll let you know in your profile that ALRO GAMES has added new rewards &#45; like exclusive collector's items or rare game-related rewards and experiences that you would expect from ALRO GAMES.<br />
      <br />
      <strong>How can I enroll</strong><br />
      <br />
      Just about. Do you have a pulse? Are you at least 13 years old? Legally reside in                  Europe.? Have a valid email address? If you can say &ldquo;yes&rdquo; to all four, you can                  join.<br />
      <br />
      <strong>Cancel Membership </strong><br />
      <br />
      What, are you nuts? We can't imagine why you'd want to cancel a free membership                  that gives you all this stuff. But you can at any time by notifying AlRO GAMES Customer                  Service by snail mail, email or telephone.              <br />
      <strong><br />
        Can I reactivate my account after I have canceled it
        </strong><br />
      <br />
      No. And be careful. Once you cancel an account, all your earned points are forfeited.                  Although, you can sign up again and receive a different membership number.              <br />
      <br />
      <br />
      <br />
      <br />
  </div>
</div>
</div>
<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="viewerFooter.jsp" />


</div>

</body>
</html>