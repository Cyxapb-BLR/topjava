<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<style>
    .normal {
        color: green;
    }

    .excess {
        color: red;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1" cellpadding="15" cellspacing="0">
<tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
</tr>
<c:forEach items="${meals}" var="meal">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
    <tr class="${meal.excess ? 'excess' : 'normal'}">
        <td>${meal.dateTime.toString().replace("T", " ")}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td>Update</td>
        <td>Delete</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
