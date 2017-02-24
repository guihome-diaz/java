<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Daxiongmao server landing page">
    <meta name="keywords" content="Daxiongmao">
    <meta name="author" content="Guillaume Diaz">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Access the bootstrap Css like this, spring boot will handle the resource mapping automatically. -->
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    
    <!--
    <spring:url value="/css/main.css" var="springCss" />m
    <link href="${springCss}" rel="stylesheet" />
     -->
    <c:url value="/css/main.css" var="jstlCss" />
    <link href="${jstlCss}" rel="stylesheet" />
    
    <title>DAXIONGMAO server</title>
</head>
    
<body>
    <p>
        Welcome to the DAXIONGMAO server!
    </p>
    <p>
        You are running: 
        <ul>
            <li>Artefact: {info.app.name}</li>
            <li>Version: {info.app.version}</li>
            <li></li>
        </ul>
    </p>
    <p>
        You can access some tools:
        <ul>
            <li><a href='swagger-ui.html'>Swagger</a></li>
        </ul>
    </p>
</body>


</html>