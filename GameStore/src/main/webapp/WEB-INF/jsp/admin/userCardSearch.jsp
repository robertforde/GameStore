<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<div class="title">
		<div id="title_card">${userObj.title} ${userObj.forename} ${userObj.surname}</div>
		<div id="balance">&#8364;<fmt:formatNumber type="number" minFractionDigits="2" value="${userObj.balance}" /></div>
	</div>
  
	<div id="search">
		<form id="form1" name="form1" method="post" action="goSearchGames">
			<p>
				<label for="text"></label>
				<input name="name" type="text" id="text" size="20" />
				<select name="platform" id="jumpMenu" onchange="MM_jumpMenu('parent',this,0)">
					<option value="0" selected>All Platforms</option>
					<option value="3">PS 3</option>
					<option value="1">PS 4</option>
					<option value="4">XBox 360</option>
					<option value="2">XBox One</option>
					<option value="5">WII</option>
					<option value="6">3DS</option>
					<option value="7">PC</option>
				</select> 
				<input type="submit" name="search" id="search2" value="Submit" />
			</p>
		</form>
	</div>

</html>