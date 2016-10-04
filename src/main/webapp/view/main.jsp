<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <head>
        <title>SQLCmd</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
                   integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link type="text/css" rel="stylesheet" href="${ctx}/resources/css/main.css" />
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/main.js"></script>
        <script type="text/javascript">
            $(window).load(function(){
                init('${ctx}');
            });
        </script>
    </head>
    <body>
    <header class="container">
        <div class="row">
            <h1 class="col-sm-4">SQLCmd</h1>
            <nav class="col-sm-8 text-right">
                <p>newest</p>
                <p>catalogue</p>
                <p>contact</p>
            </nav>
        </div></header>

        <div id="loading" style="display:none;">Loading...</div>
        <%@include file="tables.jsp" %>
        <%@include file="databases.jsp" %>
        <%@include file="menu.jsp" %>
        <%@include file="table.jsp" %>
        <%@include file="connect.jsp" %>
        <%@include file="actions.jsp" %>
        <%@include file="footer.jsp" %>
    </body>
</html>