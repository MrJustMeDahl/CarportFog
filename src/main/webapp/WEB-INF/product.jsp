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
        <h1><u>Her kan du se vores produkter!</u></h1>

        <br/>

        <h2>Carport med Fladt tag</h2>
        <form action="carport" method="get" id="noShedForm">

            <input type="image" src="${pageContext.request.contextPath}/images/carportFladt.jpg" width="400px;" class="img-fluid" alt="Submit">
            <input type="hidden" name="shed" value=0>
        </form>


        <h2>Carport med Fladt tag inkl. skur</h2>

        <form action="carport" method="get" id="shedForm">

            <input type="image" src="${pageContext.request.contextPath}/images/carportmedskur.jpg" width="400px;" class="img-fluid" alt="Submit">
            <input type="hidden" name="shed" value="1">
        </form>

        <br/>


    </jsp:body>
</t:pagetemplate>