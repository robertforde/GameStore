<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Admin Airo Games</title>
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
  <div class="title">User's Account History</div>

	<!-- Only show dropdown if transactions not passed -->
  	<c:if test="${empty account}">

  		<form action="goAccountHistory" method="get">
  	
	  		<select name="userId">
  				<c:forEach var="row" items="${listUsers}">
  					<c:choose>
  						<c:when test="${selectedOption == row.id}">
  							<option value="${row.id}">${row.email}</option>
		  				</c:when>
  						<c:otherwise>
  							<option value="${row.id}">${row.email}</option>
  						</c:otherwise>
	  				</c:choose>
	  			</c:forEach>
  			</select>
  			<input type="submit" value="Submit" />
	  	</form>
	</c:if>
	<c:if test="${!empty account}">
		${account}
		  <br/>
  
	  	<div id="container">
	  		<table border="0">
  		 		<col width="13%" />
			  	<col width="23%" />
  				<col width="40%" />
			  	<col width="10%" />
  				<col width="14%" />
		        <tr>
    	    	<td><strong>Date</strong></td>
		        <td><strong>Transaction</strong></td>
    		    <td><strong>Game</strong></td>
	    	    <td class="numRight"><strong>Amount</strong></td>
    	    	<td class="numRight"><strong>Balance after</strong></td>
		      </tr>
    		  <c:forEach var="row" items="${listLogs}">
	    	  	<tr>
    	    		<td>${row.date}</td>
	        		<td>${row.transaction}</td>
	    	    	<td>${row.gameName}</td>
    	    		<td class="numRight">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.amount}" /></td>
        			<td class="numRight">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.balanceAfter}" /></td>
	      		</tr>
	    	  </c:forEach>
		    </table>
    	</div>
	</c:if>
  
  
</div>

<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="adminFooter.jsp" />


</div>
</body>
</html>
