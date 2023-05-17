<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Nye ordre
    </jsp:attribute>

    <jsp:body>

        <div class="text-center">
            <div class="card">
                <div class="card-body">
                    <h1>Nye ordre:</h1>
                    <table class="table table-hover align-center">
                        <form>
                            <thead>
                            <tr class="table-success">
                                <th>Ordre nr:</th>
                                <th>Kunde navn:</th>
                                <th>Kunde mail:</th>
                                <th>Kunde tlf:</th>
                                <th>Carport længde:</th>
                                <th>Carport bredde:</th>
                                <th>Carport højde:</th>
                                <th>Kostpris:</th>
                                <th>Salgspris:</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${sessionScope.newOrders}" var="orders">
                                <tr>
                                    <c:forEach items="${sessionScope.newOrdersUsers}" var="user">
                                        <c:if test="${orders.userID == user.userID}">
                                            <c:choose>
                                                <c:when test="${requestScope.chosenOrderID != orders.orderID}">
                                            <td>${orders.orderID}</td>
                                            <td>${user.name}</td>
                                            <td>${user.email}</td>
                                            <td>${user.phoneNumber}</td>
                                            <td>${orders.carport.length}</td>
                                            <td>${orders.carport.width}</td>
                                            <td>${orders.carport.minHeight}</td>
                                            <td>${orders.price}</td>
                                            <td>${orders.indicativePrice}</td>
                                            <td>
                                                <button class="btn btn-outline-success" name="orderID"
                                                        value="${orders.orderID}" type="submit"
                                                        formaction="adminneworders" formmethod="get">Lav tilbud
                                                </button>
                                            </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>${orders.orderID}</td>
                                                    <td>${user.name}</td>
                                                    <td>${user.email}</td>
                                                    <td>${user.phoneNumber}</td>
                                                    <td>${orders.carport.length}</td>
                                                    <td>${orders.carport.width}</td>
                                                    <td>${orders.carport.minHeight}</td>
                                                    <td>${orders.price}</td>
                                                    <td><input type="number" class="table-responsive" value="${orders.indicativePrice}"></td>
                                                    <td>
                                                        <button class="btn btn-outline-success" name="orderID"
                                                                value="${orders.orderID}" type="submit"
                                                                formaction="sendoffer" formmethod="post">Send tilbud
                                                        </button>
                                                        <button class="btn btn-outline-danger mt-1" name="orderID"
                                                                value="${orders.orderID}" type="submit"
                                                                formaction="admindeleteoffer" formmethod="post">Slet tilbud
                                                        </button>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </form>
                    </table>
                </div>
            </div>
        </div>

    </jsp:body>
</t:pagetemplate>