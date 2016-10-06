<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="menu" class="row text-center" style="display:none;">
    <div>Existing commands:</div>
        <dl class="container">
            <script template="row" type="text/x-jquery-tmpl">
                <a class="text-primary" href="#/{{= command}}">{{= command}}</a></br>
                <dd>{{= description}}</dd>
            </script>
        </dl>
    </div>
</div>