<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
    Существующие команды:<br>
    <a href="connect">connect</a><br>
    &nbsp;&nbsp;&nbsp;&nbsp;для подключения к базе данных, с которой будем работать<br>

    <a href="tables">tables</a><br>
    &nbsp;&nbsp;&nbsp;&nbsp;для получения списка всех таблиц базы, к которой подключились<br>
        clear|tableName<br>

    &nbsp;&nbsp;&nbsp;&nbsp;для очистки всей таблицы<br>
        create|tableName|column1|value1|column2|value2|...|columnN|valueN<br>

    &nbsp;&nbsp;&nbsp;&nbsp;для создания записи в таблице<br>
        find|tableName<br>

    &nbsp;&nbsp;&nbsp;&nbsp;для получения содержимого таблицы tableName<br>
        help<br>

    &nbsp;&nbsp;&nbsp;&nbsp;для вывода этого списка на экран<br>
        exit<br>

    &nbsp;&nbsp;&nbsp;&nbsp;для выхода из программы<br>
        You can goto <a href="menu">Menu</a><br>
    </body>
</html>