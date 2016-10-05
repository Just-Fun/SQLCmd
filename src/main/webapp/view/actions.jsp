<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="actions">
<div class="row text-center">User actions:</div>
    <table border="1" class="container">
        <tr>
            <td>Username</td>
            <td>Actions</td>
            <td>Database</td>
            <td>Date</td>
        </tr>
        <script template type="text/x-jquery-tmpl">
            <tr>
                <td>
                    {{= username}}
                </td>
                <td>
                    {{= action}}
                </td>
                 <td>
                    {{= database}}
                </td>
                 <td>
                    {{= date}}
                </td>
            </tr>
        </script>
    </table>
</div>