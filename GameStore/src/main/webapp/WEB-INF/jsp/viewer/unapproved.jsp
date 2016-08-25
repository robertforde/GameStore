<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>ALRO GAMES AWAITING APPROVEL</title>

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

<jsp:include page="viewerSearchForm.jsp" />

<div id="box2">
  <div class="title">Awaiting approvel</div>
  <div class="boxcontaints2"> 
    <h1><img src="<c:url value='/resources/images/pacman.jpg' />" width="376" height="242" /> <br />
      Your account is awaiting approval<br />
      </h1>
    <div class="account">Your account has been created but needs to be approved by an administrator<br />
      before you can sign in.<br />
      <br />
      You will contact the administrator who will approve your account<br />
      <br />
      Once it's been approved you will be abile to login.<br />
      <br />
      Thank you for your patience
<br />
    </div>
    <h1><br />
    </h1>
      </div>
</div>
</div>
<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="viewerFooter.jsp" />


</div>

</body>
</html>