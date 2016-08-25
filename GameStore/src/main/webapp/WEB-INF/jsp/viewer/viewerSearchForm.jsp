<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<div id="search">
  <form id="form1" name="form1" method="post" action="goSearchGames">
    <label for="textfield"></label>
    <input name="name" type="text" id="textfield" size="100" value="${name}"/>
    <select name="platform" id="jumpMenu">
      <option value="0" selected="selected">All Platforms</option>
        <option value="3">PS 3</option>
        <option value="1">PS 4</option>
        <option value="4">XBox 360</option>
        <option value="2">XBox One</option>
        <option value="5">WII</option>
        <option value="6">3DS</option>
        <option value="7">PC</option>
    </select>
    <input type="submit" name="search2" id="search2" value="Submit" />
  </form>
</div>

</html>