<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>ALRO GAMES REGISTRATION</title>

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
  <div class="title">Registration</div>
  <div id="boxcontaints">
    <div class="registration">
      <form id="form2" name="form2" method="post" action="goRegister">
        <table width="100%" border="0">
          <tr>
            <td width="17%">&nbsp;</td>
            <td width="83%">&nbsp;</td>
          </tr>
          <tr>
            <td>User name :</td>
            <td><label for="email"></label>
              <input type="text" name="email" id="email" size="30" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Password :</td>
            <td><label for="password"></label>
              <input type="password" name="password" id="password" size="30" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Title :</td>
            <td><label for="title2"></label>
              <input type="text" name="title" id="title2" size="4" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="24">Forename :</td>
            <td><label for="forename"></label>
              <input type="text" name="forename" id="forename" size="30" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Surname :</td>
            <td><label for="surname"></label>
              <input type="text" name="surname" id="surname" size="30" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Home Phone :</td>
            <td><input type="text" name="homePhone" id="homePhone" size="15" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Mobile</td>
            <td><label for="mobile Number"></label>
              <input type="text" name="mobile" id="mobile Number" size="15" />              <label for="homePhone"></label></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Address</td>
            <td><label for="address1"></label>
              <input type="text" name="address" id="address1" size="50" /></td>
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
