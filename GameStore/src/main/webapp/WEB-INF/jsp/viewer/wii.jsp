<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ALRO GAMES WII</title>
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

<div id="box">
  <div class="title2">Special Offers</div>
  <div class="content_special">
    <div id="special_offer1">
      <map name="Map" id="Map">
        <area shape="rect" coords="4,7,267,112" href="register.html" />
      </map>
    </div>
    
    <div id="special_offer2">
      <map name="Map2" id="Map2">
        <area shape="rect" coords="8,6,264,115" href="batmandownload.html" alt="batman download" />
      </map>
    </div>
    
  </div>
</div>
<div id="box1">
  <div class="title">5 Most Viewed Games</div>
  <div id="boxcontaints">
	WII
    <c:forEach var="row" items="${ViewsWII}" >
    		<div class="results2">
		    	
	      		<div class="product_image"><a href="goViewGame?gameId=${row.gId}"><img src="<c:url value='${row.location}' />" width="108" height="135" border="0" /></a></div>
    	  		<div class="productinfo2">
 		  		<div class="title_info">${row.viewsGame.name}</div>
          		<div class="publisher">${row.viewsGame.publisher}</div>
      	  	</div>
	      	<div class="level_membership">
    	    	<div class="gold">
    	    		<div class="price">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.viewsGame.goldPrice}"></fmt:formatNumber></div>
					<img src="<c:url value='/resources/images/gold.png' />" width="26" height="40" alt="gold" /> 
				</div>

        		<div class="sliver">
	        		<div class="price">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.viewsGame.silverPrice}"></fmt:formatNumber></div>
        			<img src="<c:url value='/resources/images/sliver.png' />"  alt="sliver" width="26" height="40" />
	        	</div>
        
        
    	    	<div class="bronze">
        			<div class="price">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${row.viewsGame.bronzePrice}"></fmt:formatNumber></div>
        			<img src="<c:url value='/resources/images/bronze.png' />" alt="bronze" width="26" height="40" />
	        	</div>

    	  	</div>
      	
    	</div>
    </c:forEach>
  </div>
</div>

</div>

<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="viewerFooter.jsp" />


<!--End of wrapper------------------------------------------------------------------------------------------------------------------->
</div>
</body>
</html>