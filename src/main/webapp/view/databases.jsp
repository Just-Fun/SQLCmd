<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="databases" class="row text-center">
    <div>Existing databases:</div>
    <div class="container">
        <script template type="text/x-jquery-tmpl">
            <dl>
                <dt>{{= $data}}</dt>
            </dl>

        </script>
    </div>
</div>