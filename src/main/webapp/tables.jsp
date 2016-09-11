<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
    Tables:<br>
        <c:forEach items="${tables}" var="table">
            <a href="${table}">${table}</a><br>
        </c:forEach>
    <br>
    <a href="menu">menu</a>
    </body>
</html>