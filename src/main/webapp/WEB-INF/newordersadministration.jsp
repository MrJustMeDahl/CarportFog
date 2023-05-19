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
        <div class="row">
            <div class="card d-block d-inline col-2">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${requestScope.chosenOrderID != null}">
                            <c:forEach items="${sessionScope.newOrders}" var="order">
                                <c:if test="${order.orderID == requestScope.chosenOrderID}">
                                    <c:forEach items="${sessionScope.newOrdersUsers}" var="user">
                                        <c:if test="${order.userID == user.userID}">
                                            <h4>Ordre nr:</h4>
                                            <p>${order.orderID}</p>
                                            <h4>Kunde nr:</h4>
                                            <p>${user.userID}</p>
                                            <h4>Kundenavn:</h4>
                                            <p>${user.name}</p>
                                            <h4>Email:</h4>
                                            <p>${user.email}</p>
                                            <h4>Tlf nr:</h4>
                                            <p>${user.phoneNumber}</p>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <h4>Ordre nr:</h4>
                            <p></p>
                            <h4>Kunde nr:</h4>
                            <p></p>
                            <h4>Kundenavn:</h4>
                            <p></p>
                            <h4>Email:</h4>
                            <p></p>
                            <h4>Tlf nr:</h4>
                            <p></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="d-inline d-block col-10 text-center">
                <table class="table table-responsive table-hover">
                    <thead>
                    <tr class="table-success">
                        <th>Carport længde:</th>
                        <th>Carport bredde:</th>
                        <th>Carport højde:</th>
                        <th>Skur længde:</th>
                        <th>Skur bredde:</th>
                        <th>Kostpris:</th>
                        <th>Salgspris:</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <form>
                        <c:forEach items="${sessionScope.newOrders}" var="order">
                            <c:choose>
                                <c:when test="${requestScope.chosenOrderID == order.orderID}">
                                    <tr>
                                        <td>
                                            <input style="width: 100px" type="number" class="table-responsive"
                                                   value="${order.carport.length}" name="length">
                                        </td>
                                        <td>
                                            <input style="width: 100px" type="number" class="table-responsive"
                                                   value="${order.carport.width}" name="width">
                                        </td>
                                        <td>
                                            <input style="width: 100px" type="number" class="table-responsive"
                                                   value="${order.carport.minHeight}" name="minHeight">
                                        </td>
                                        <td>
                                            <input style="width: 100px" type="number" class="table-responsive"
                                                   value="${order.carport.shed.length}" name="shedLength">
                                        </td>
                                        <td>
                                            <input style="width: 100px" type="number" class="table-responsive"
                                                   value="${order.carport.shed.width}" name="shedWidth">
                                        </td>
                                        <td>${order.price}</td>
                                        <td>
                                            <input style="width: 100px" type="number" class="table-responsive"
                                                   value="${order.indicativePrice}" name="salesPrice">
                                        </td>
                                        <td>
                                            <button class="btn btn-outline-success" name="orderID"
                                                    value="${order.orderID}" type="submit"
                                                    formaction="adminsendoffer" formmethod="post">Send tilbud
                                            </button>
                                            <button class="btn btn-outline-primary mt-1" name="orderID"
                                                    value="${order.orderID}" type="submit"
                                                    formaction="adminupdateorder" formmethod="post">Opdater
                                                ordre
                                            </button>
                                            <button class="btn btn-outline-danger mt-1" name="orderID"
                                                    value="${order.orderID}" type="submit"
                                                    formaction="admindeleteoffer" formmethod="post">Slet tilbud
                                            </button>
                                        </td>
                                    </tr>
                                    <tr class="table-primary">
                                        <th colspan="10">Stykliste</th>
                                    </tr>
                                    <tr class="table-primary">
                                        <th colspan="3">Beskrivelse:</th>
                                        <th colspan="2">Længde:</th>
                                        <th colspan="2">Antal:</th>
                                        <th colspan="3">Note:</th>
                                    </tr>
                                    <c:forEach items="${order.itemList.materials}" var="itemListMaterial">
                                        <tr>
                                            <c:choose>
                                                <c:when test="${itemListMaterial.material.materialID != 17}">
                                                    <td colspan="3">${itemListMaterial.material.description}</td>
                                                    <td colspan="2">${itemListMaterial.material.length}</td>
                                                    <td colspan="2">${itemListMaterial.amount}</td>
                                                    <td colspan="3">${itemListMaterial.message}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td colspan="3">${itemListMaterial.message}</td>
                                                    <td colspan="2">${itemListMaterial.actualLength}</td>
                                                    <td colspan="2">${itemListMaterial.amount}</td>
                                                    <td colspan="3">${itemListMaterial.message}</td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>${order.carport.length}</td>
                                        <td>${order.carport.width}</td>
                                        <td>${order.carport.minHeight}</td>
                                        <td>${order.carport.shed.length}</td>
                                        <td>${order.carport.shed.width}</td>
                                        <td>${order.price}</td>
                                        <td>${order.indicativePrice}</td>
                                        <td>
                                            <button class="btn btn-outline-success" name="orderID"
                                                    value="${order.orderID}" type="submit"
                                                    formaction="adminneworders" formmethod="get">Lav tilbud
                                            </button>
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form>
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:body>
</t:pagetemplate>