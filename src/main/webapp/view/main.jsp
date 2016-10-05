<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <head>
        <title>SQLCmd</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
                   integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js"></script>
        <link type="text/css" rel="stylesheet" href="${ctx}/resources/css/main.css" />
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
                <h2 class="col-sm-1">SQLCmd</h2>
                <nav class="col-sm-11 text-right">
                    <a href="main#/menu">menu</a></br>
                    <a href="main#/connect">connect</a></br>
                    <a href="main#/tables">tables</a></br>
                    <a href="main#/databases">databases</a></br>
                    <a href="main#/actions">actions</a></br>
                </nav>
            </div>
        </header>

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