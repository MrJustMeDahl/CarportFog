<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">

    </jsp:attribute>

    <jsp:body>
        <h1>Indkøbskurv</h1>

        <br/>
        <br/>
        <table class="table">
            <thead>
            <th>Pris: ${requestScope.order.price} kr</th>
            <th>Vejl. Pris: ${requestScope.order.indicativePrice} kr</th>
            <th>Ordre Status: "${requestScope.order.orderStatus}"</th>
            <th>Længde: ${requestScope.order.carport.length} cm</th>
            <th>Bredde: ${requestScope.order.carport.width} cm</th>
            <th>Højde: ${requestScope.order.carport.minHeight} cm</th>
            </thead>
        <tr></tr>

        </table>
        <form action="orderandpayment" method="post">
            <button class="btn btn-outline-primary" type="submit" value="${requestScope.order.orderID}" name="OrderId">Få Tilbud</button>
        </form>


    </jsp:body>
</t:pagetemplate>