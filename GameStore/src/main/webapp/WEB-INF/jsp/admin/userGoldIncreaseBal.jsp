<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ALRO GAMES INCREASE BALANCE</title>
	<link href="<c:url value='/resources/css/user_styles.css' />" rel="stylesheet" />

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

	<jsp:include page="userCardSearch.jsp" />
  
	<div id="title_name">Increase Your Balance</div>
	<div id="container">
		<form id="form1" name="form1" method="post" action="goIncreaseBalance">
			<table width="55%" border="0">
				<tr>
					<td>Increase Balance:</td>
					<td width="68%"><label for="forename"></label>
					<input type="text" name="increase" id="forename" /></td>
				</tr>
        		<tr>
					<td colspan="2" align="right"><input type="submit" name="update" id="update" value="Update" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>



<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="userFooter.jsp" />


</div>
</body>
</html>
