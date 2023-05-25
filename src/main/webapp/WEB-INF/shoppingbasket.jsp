<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Indkøbskurv
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <h1><u>Indkøbskurv</u></h1>
        </div>
        <c:if test="${!requestScope.message.equals('Din indkøbskurv er tom') || requestScope.order == null}">
            <div class="row mt-3">
                <h5> ${requestScope.message}</h5>
            </div>
        </c:if>
        <c:if test="${requestScope.order != null}">
            <div class="card mx-auto w-50">
                <c:choose>
                    <c:when test="${requestScope.order.carport.shed.length != 0}">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/images/carportmedskur.jpg">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img-top" src="${pageContext.request.contextPath}/images/carportFladt.jpg">
                    </c:otherwise>
                </c:choose>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${requestScope.order.carport.shed.length != 0}">
                            <h3 class="card-title">Carport med fladt tag inkl. skur</h3>
                        </c:when>
                        <c:otherwise>
                            <h3 class="card-title">Carport med fladt tag</h3>
                        </c:otherwise>
                    </c:choose>
                    <table class="table table-hover table-responsive">
                        <thead>
                        <th></th>
                        <th>Længde:</th>
                        <th>Bredde:</th>
                        <th>Højde:</th>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Carport</td>
                            <td>${requestScope.order.carport.length}</td>
                            <td>${requestScope.order.carport.width}</td>
                            <td>${requestScope.order.carport.minHeight}</td>
                        </tr>
                        <c:if test="${requestScope.order.carport.shed.length != 0}">
                            <tr>
                                <td>Skur</td>
                                <td>${requestScope.order.carport.shed.length}</td>
                                <td>${requestScope.order.carport.shed.width}</td>
                                <td>${requestScope.order.carport.minHeight}</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                    <div class="row">
                        <div class="col-8">
                        </div>
                        <div class="col-4">
                            <h5>Vejl. pris ${requestScope.order.indicativePrice}</h5>
                        </div>
                    </div>
                    <form>
                        <div class="row">
                            <button class="btn btn-primary mx-auto mt-1" formaction="orderandpayment" formmethod="post"
                                    value="${requestScope.order.orderID}" name="OrderId" type="submit">
                                <img src="${pageContext.request.contextPath}/images/indkøbskurv.png" class="btn-icon"
                                     style="width: 10%">
                                Bestil tilbud
                            </button>
                            <button class="btn btn-danger mx-auto mt-1" formaction="orders" formmethod="post"
                                    value="${requestScope.order.orderID}" name="currentID" type="submit">
                                Annuller ordre
                            </button>
                            <input type="hidden" name="shoppingDelete" value="true">
                        </div>
                    </form>
                </div>
            </div>
        </c:if>

    </jsp:body>
</t:pagetemplate>