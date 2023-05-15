<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Opret konto
    </jsp:attribute>

    <jsp:attribute name="footer">
            Opret konto
    </jsp:attribute>

    <jsp:body>

        <h3>Du kan Oprette her</h3>

        <form action="signup" method="post">
            <label for="email">Email: </label>
            <input type="text" id="email" name="email"/>
            <label for="password">Kodeord: </label>
            <input type="password" id="password" name="password"/>
            <label for="fullName">Fulde Navn:</label>
            <input type="fullName" id="fullName" name="fullName"/>
            <label for="phoneNumber">Telefon nummer:</label>
            <input type="phoneNumber" id="phoneNumber" name="phoneNumber"/>
            <label for="address">addresse</label>
            <input type="address" id="address" name="address"/>
            <input type="submit"  value="Opret"/>

        </form>


    </jsp:body>
</t:pagetemplate>