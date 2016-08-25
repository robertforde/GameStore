<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<div id="nav_admin">
		<ul>
			<c:choose>
				<c:when test="${userObj.level=='Bronze'}">
					<li><a href="goBronzeHomePage">Home</a></li>
					<li><a href="goBronzeIncreaseBalPage">Increase Balance</a></li>
				</c:when>
				<c:when test="${userObj.level=='Silver'}">
					<li><a href="goSilverHomePage">Home</a></li>
					<li><a href="goSilverIncreaseBalPage">Increase Balance</a></li>
				</c:when>
				<c:when test="${userObj.level=='Gold'}">
					<li><a href="goGoldHomePage">Home</a></li>
					<li><a href="goGoldIncreaseBalPage">Increase Balance</a></li>
				</c:when>
			</c:choose>
			<li><a href="goChangeDetails">Change Details</a></li>
			<li><a href="goAccountHistory">Account History</a></li>
			<li><a href="goLogOut">Log out</a></li>
		</ul>
	</div>

</html>