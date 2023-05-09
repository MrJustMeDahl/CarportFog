<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">

    </jsp:attribute>

    <jsp:body>
        <h1>Her kan du se vores produkter!</h1>

        <br/>

        <h2>Carport med Fladt tag</h2>
        <a>
            <img src="${pageContext.request.contextPath}/images/carportFladt.jpg" width="400px;" class="img-fluid"/>
        </a>
        <form action="carport" method="get">
            <input type="submit" value="Vælg">
        </form>

        <br/>


    </jsp:body>
</t:pagetemplate>