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
        <h1><u>Indkøbskurv</u></h1>

        <br/>
        <br/>
        <h5> ${requestScope.message}</h5>
        <br/>
        <c:if test="${requestScope.order.carport.checkShed == true}">
            <h4>Carport med fladt tag inkl. skur</h4>
        </c:if>
        <c:if test="${requestScope.order.carport.checkShed == false}">
            <h4>Carport med fladt tag</h4>
        </c:if>





        <c:if test="${requestScope.order.orderID == null}">
            <h5>Din indkøbskurv er tom!</h5>
        </c:if>
        <c:if test="${requestScope.order.orderID != null}">
        <table class="table">

            <thead>
            <th>Vejl. Pris: ${requestScope.order.indicativePrice} kr</th>
            <th>Ordre Status: "${requestScope.order.orderStatus}"</th>
            <th>Længde: ${requestScope.order.carport.length} cm</th>
            <th>Bredde: ${requestScope.order.carport.width} cm</th>
            <th>Højde: ${requestScope.order.carport.minHeight} cm</th>
            <th>Skur Længde: ${requestScope.order.carport.shed.length} cm</th>
            <th>Skur Bredde: ${requestScope.order.carport.shed.width} cm</th>
            </thead>

        <tr></tr>

        </table>

        <form action="orderandpayment" method="post">
            <button class="btn btn-outline-primary" type="submit" value="${requestScope.order.orderID}" name="OrderId">Få Tilbud</button>
        </form>
        <form action="orders" method="post">
            <button class="btn btn-outline-primary" type="submit" value="${requestScope.order.orderID}" name="currentID">Annuller</button>
            <input type="hidden" name="shoppingDelete" value="true">
        </form>
        </c:if>



    </jsp:body>
</t:pagetemplate>