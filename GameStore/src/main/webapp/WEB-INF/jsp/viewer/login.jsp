<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>ALRO GAMES</title>

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
  <div class="title">Login </div>
  <div id="boxcontaints">
    <div class="login">
      <form id="form2" name="form2" method="post" action="goLogin">
        <table width="100%" border="0">
          <tr>
            <td width="21%">&nbsp;</td>
            <td width="79%">&nbsp;</td>
            </tr>
          <tr>
            <td>E-Mail :</td>
            <td><label for="email"></label>
              <input name="email" type="text" id="email" maxlength="67" size="30" /></td>
            </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td>Password:</td>
            <td><label for="password"></label>
              <input type="password" name="password" id="password" size="30" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="Submit" id="Submit" value="Submit" /></td>
          </tr>
        </table>
        <br />
      </form>
    </div>
  </div>
</div>
</div>
<!--End of Main-------------------------------------------------------------------------------------------------------------------->


<jsp:include page="viewerFooter.jsp" />


</div>
</body>
</html>
