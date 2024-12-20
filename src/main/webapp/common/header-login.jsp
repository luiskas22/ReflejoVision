<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.luis.reflejovision.model.*"%>
<%@ page import="java.util.List"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="i18n.Messages" var="messages"/>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/login.css'/>"/>
    <link rel="icon" href="<c:url value='/images/rvLogo.ico'/>" type="image/x-icon">

<title>Reflejo Vision</title>
</head>
<body>
