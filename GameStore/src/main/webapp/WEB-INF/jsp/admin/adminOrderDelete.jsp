<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ALRO GAMES - Delete Orders</title>
	<link href="<c:url value='/resources/css/admin_styles.css' />" rel="stylesheet" />

</head>

<body style="margin:0; padding:0">


<!--Wrapper this is the start of the wrapper Wrapper is width 1000px and height is auto, it will contain the following header, log navbar, main and footer. which will be displayed below.-->
<div id="wrapper">

<!--Header-->

<div id="header">

<!--Logo alro-games-->

<div id="logo"><img src="<c:url value='/resources/images/logo.png' />" width="261" height="100" alt="alro logo" /> </div> 


<!--End of alro-games-->

<jsp:include page="adminNav.jsp" />

</div>

<!--End Header-->

<!--navigation--><!--Main--------------------------------------------------------------------------------------------------------------------------->
<!--generally contains all stuff with in the page, this concludes banners, display boxes of verious offers and softwate releases.-->

<div id="main">
  <div class="title">Delete Orders</div>
  <div id="dropdownmenu">    Analysis Charts: 
    <form name="analysis" action="goAdminHome" method="get">
      <select name="platform">
        <option value="0" selected="selected">All Platforms</option>
        <option value="3">PS 3</option>
        <option value="1">PS 4</option>
        <option value="2">XBox 360</option>
        <option value="4">XBox One</option>
        <option value="5">WII</option>
        <option value="6">3DS</option>
        <option value="7">PC</option>
      </select>
      <input type="submit" value="Submit"  />
    </form>
  </div>
  <div id="container">
    
  	<table>
		<tr>
			<th>User E-Mail</th>
			<th>User Name</th>
			<th>Game</th>
			<th>Price</th>
			<th>Status</th>
			<th>Balance</th>
			<th></th>
		</tr>
		
		<c:forEach var="row" items="${listDeleteOrders}" >
			<tr>
			
				<td>${row.user.email}</td>
				
				<td>${row.user.title} ${row.user.forename} ${row.user.surname}</td>
				
				<td>${row.game.name}</td>
				
				<c:if test="${row.user.level == 'Bronze'}">
					<td class="numRight">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.game.bronzePrice}" /></td>
				</c:if>
				
				<c:if test="${row.user.level == 'Silver'}">
					<td class="numRight">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.game.silverPrice}" /></td>
				</c:if>
				
				<c:if test="${row.user.level == 'Gold'}">
					<td class="numRight">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.game.goldPrice}" /></td>
				</c:if>
				
				<td>${row.status}</td>
				
				<td class="numRight">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.user.balance}" /></td>
				
				<td><a href="goOrderDelete?orderId=${row.id}"><img src="<c:url value='/resources/images/trashcan2.png' />" /></a></td>
				
			</tr>
		</c:forEach>
	</table>
	
	</div>
</div>

<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="adminFooter.jsp" />


</div>
</body>
</html>
