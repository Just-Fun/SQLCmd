<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="actions">
<div class="row text-center">User actions:</div>
    <table border="1" class="container">
        <tr>
            <td><strong>Username</strong></td>
            <td><strong>Actions</strong></td>
            <td><strong>Database</strong></td>
            <td><strong>Date</strong></td>
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