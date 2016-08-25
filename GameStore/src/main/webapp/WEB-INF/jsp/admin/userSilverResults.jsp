<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ALRO GAMES GOLD MEMBERSHIP SEARCH</title>
	<link href="<c:url value='/resources/css/silver_styles.css' />" rel="stylesheet" />

</head>

<body style="margin:0; padding:0">


<!--Wrapper this is the start of the wrapper Wrapper is width 1000px and height is auto, it will contain the following header, log navbar, main and footer. which will be displayed below.-->
<div id="wrapper">

<!--Header-->

<div id="header">

<!--Logo alro-games-->

<div id="logo"><img src="<c:url value='/resources/images/logo.png' />" width="261" height="100" alt="alro logo" /> </div> 


<!--End of alro-games-->

<jsp:include page="userNav.jsp" />

</div>

<!--End Header-->

<!--navigation--><!--Main--------------------------------------------------------------------------------------------------------------------------->
<!--generally contains all stuff with in the page, this concludes banners, display boxes of verious offers and softwate releases.-->

<div id="main">

  <div class="title">
		<div id="title_card">${userObj.title} ${userObj.forename} ${userObj.surname}</div>
		<div id="balance">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${userObj.balance}" /></div>
	</div>
  
	<div id="search">
		<form id="form1" name="form1" method="post" action="goSearchGames">
			<p>
				<label for="text"></label>
				<input name="name" type="text" id="text" size="20" value="${name}" />
				<select name="platform" id="jumpMenu" onchange="MM_jumpMenu('parent',this,0)">
					<c:choose>
						<c:when test="${platform==0}">
							<option value="0" selected="selected">All Platforms</option>
						</c:when>
						<c:otherwise>
							<option value="0">All Platforms</option>
						</c:otherwise>
					</c:choose>
	      	
					<c:choose>
						<c:when test="${platform==3}">
							<option value="3" selected="selected">PS 3</option>
						</c:when>
						<c:otherwise>
							<option value="3">PS 3</option>
						</c:otherwise>
					</c:choose>
	        
					<c:choose>
						<c:when test="${platform==1}">
							<option value="1" selected="selected">PS 4</option>
						</c:when>
						<c:otherwise>
	    					<option value="1">PS 4</option>
	    				</c:otherwise>
					</c:choose>
	        
					<c:choose>
						<c:when test="${platform==4}">
							<option value="4" selected="selected">XBox 360</option>
						</c:when>
						<c:otherwise>
							<option value="4">XBox 360</option>
						</c:otherwise>
					</c:choose>
	        
					<c:choose>
						<c:when test="${platform==2}">
							<option value="2" selected="selected">XBox One</option>
						</c:when>
						<c:otherwise>
							<option value="2">XBox One</option>
						</c:otherwise>
					</c:choose>
	        
					<c:choose>
						<c:when test="${platform==5}">
							<option value="5" selected="selected">WII</option>
						</c:when>
						<c:otherwise>
							<option value="5">WII</option>
						</c:otherwise>
					</c:choose>
	        
					<c:choose>
						<c:when test="${platform==6}">
							<option value="6" selected="selected">3DS</option>
						</c:when>
						<c:otherwise>
							<option value="6">3DS</option>
						</c:otherwise>
					</c:choose>
	        
					<c:choose>
						<c:when test="${platform==7}">
							<option value="7" selected="selected">PC</option>
						</c:when>
						<c:otherwise>
							<option value="7">PC</option>
						</c:otherwise>
					</c:choose>
	        
				</select>
				<input type="submit" name="search" id="search2" value="Submit" />
			</p>
		</form>
	</div>
  
  <div id="title_name">Search Results</div>

  <div class="box2">
  	<c:if test="${fn:length(listGames) gt 0}">
	
 		<c:forEach var="row" items="${listGames}" >
    		<div class="results">
		    	
		    	<div class="product_image"><a href="goUserViewGame?gameId=${row.game.id}"><img src="<c:url value='${row.smallCoverLoc}' />" width="108" height="135" border="0" /></a></div>
    	  		<div class="productinfo">
 		  		<div class="title_info">${row.game.name}</div>
          		<div class="publisher">${row.game.publisher}</div>
          		
          		<c:if test="${userObj.level=='Gold'}">
          			<div class="cost">Your Price: ${row.game.goldPrice}</div>
          		</c:if>
          		<c:if test="${userObj.level=='Silver'}">
          			<div class="cost">Your Price: ${row.game.silverPrice}</div>
          		</c:if>
          		<c:if test="${userObj.level=='Bronze'}">
          			<div class="cost">Your Price: ${row.game.bronzePrice}</div>
          		</c:if>
          		
          		 <div class="order"><a href="goOrderGame?userId=${userObj.id}&gameId=${row.game.id}">Order</a></div>
          		
      	  	</div>
	      	<div class="level_membership">
    	    	<div class="gold">
    	    		<div class="price">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.game.goldPrice}"></fmt:formatNumber></div>
					<img src="<c:url value='/resources/images/gold.png' />" width="26" height="40" alt="gold" /> 
				</div>

        		<div class="sliver">
	        		<div class="price">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.game.silverPrice}"></fmt:formatNumber></div>
        			<img src="<c:url value='/resources/images/sliver.png' />"  alt="sliver" width="26" height="40" />
	        	</div>
        
        
    	    	<div class="bronze">
        			<div class="price">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.game.bronzePrice}"></fmt:formatNumber></div>
        			<img src="<c:url value='/resources/images/bronze.png' />" alt="bronze" width="26" height="40" />
	        	</div>

    	  	</div>
      	
    	</div>
    </c:forEach>
    </c:if>
 	<c:if test="${fn:length(listGames) eq 0}">
 		<div id="noSearchResults">No Games Found</div>
 	</c:if>
</div>

</div>

<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="userFooter.jsp" />


</div>
</body>
</html>
