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
        <div class="text-center">
        <h1><u>Kvittering</u></h1>
        <br/>
        <br/>
        <br/>


        <table class="table">

            <thead>
            <th>Pris: ${requestScope.order.indicativePrice} kr</th>
            <th>Ordre Status: "${requestScope.order.orderStatus}"</th>
            <th>Længde: ${requestScope.order.carport.length} cm</th>
            <th>Bredde: ${requestScope.order.carport.width} cm</th>
            <th>Højde: ${requestScope.order.carport.minHeight} cm</th>
            <th>Skur Længde: ${requestScope.order.carport.shed.length} cm</th>
            <th>Skur Bredde: ${requestScope.order.carport.shed.width} cm</th>
            </thead>



            <tr></tr>

        </table>

        <table class="table">
            <thead>
            <th>
                Beskrivelse:
            </th>
            <th>
                Længde:
            </th>
            <th>
                Antal:
            </th>
            <th>
                Note:
            </th>
            </thead>
            <c:forEach items="${requestScope.order.itemList.materials}" var="itemListMaterial">
                <tr>
                    <c:choose>
                        <c:when test="${itemListMaterial.material.materialID != 17}">

                            <td >${itemListMaterial.material.description}</td>
                            <td >${itemListMaterial.material.length}</td>
                            <td >${itemListMaterial.amount}</td>
                            <td >${itemListMaterial.message}</td>
                        </c:when>
                        <c:otherwise>
                            <td >${itemListMaterial.message}</td>
                            <td >${itemListMaterial.actualLength}</td>
                            <td >${itemListMaterial.amount}</td>
                            <td >${itemListMaterial.message}</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>

        </table>
</div>

        <br/>
        <br/>




    </jsp:body>
</t:pagetemplate>