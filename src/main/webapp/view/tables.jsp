<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="tables" class="row text-center">
    <div>Existing tables:</div>
    <div class="container">
        <script template type="text/x-jquery-tmpl">
            <a href="#/table/{{= $data}}">{{= $data}}</a></br>
        </script>
    </div>
</div>