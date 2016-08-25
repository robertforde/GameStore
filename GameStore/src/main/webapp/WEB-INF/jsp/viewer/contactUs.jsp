<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<title>ALRO GAMES CONTACT US</title>

	<link href="<c:url value='/resources/css/styles.css' />" rel="stylesheet" />

</head>

<body style="margin:0; padding:0">


<!--Wrapper this is the start of the wrapper Wrapper is width 1000px and height is auto, it will contain the following header, log navbar, main and footer. which will be displayed below.-->
<div id="wrapper">

<!--Header-->

<div id="header">

<!--Logo alro-games-->

<div id="logo"><img src="<c:url value='/resources/images/LOGO.jpg' />" width="261" height="100" border="0" /></div>

<!--End of alro-games-->

</div>

<jsp:include page="viewerNav.jsp" />

<!--End Header-->


<!--Main--------------------------------------------------------------------------------------------------------------------------->
<!--generally contains all stuff with in the page, this concludes banners, display boxes of verious offers and softwate releases.-->

<div id="main">

<!--banner-->

<div id="banner2"></div>

<jsp:include page="viewerSearchForm.jsp" />

<div id="box2">
  <div class="title">Contact Us </div>
  <div id="boxcontaints">
    <div class="phone_number">
      <div class="MAP"></div>
      <div id="addressinfo"><strong>Address:</strong><br />
        Block W,<br />
East Point Business Park,<br />
Dublin 3<br />
      </div>
      <div class="phonenumber"><strong>Phone:</strong> 1-800 817 333 (Call free)</div>
    </div>
 	<div class="form">
      <div id="hq"><img src="<c:url value='/resources/images/hgcontact.jpg' />" width="400" height="282" alt="alro_games" /><br />
        <br />
        Email us at: info@alro-games.com
	  </div>
 	</div>
</div>
</div>
</div>
<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="viewerFooter.jsp" />


</div>
</body>
</html>
