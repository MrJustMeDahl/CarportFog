<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">

    </jsp:attribute>

    <jsp:body>
        <div class="Card">
            <form action="post">
                <h2 class="text-center pt-5"> Alle Ordre </h2>

                <div class="card-body">

                    <table class="table table-striped">
                        <th class="text-center"><h4>Eksisterende ordre</h4></th>

                        <tr>
                            <td><p>Ordre Id</p></td>
                            <td><p>User Info</p></td>
                            <td><p>Samlet Pris</p></td>
                            <td><p>Ordre Status</p></td>
                        </tr>


                        <c:forEach var="allOrders" items="${sessionScope.allOrders}"
                                   varStatus="status">
                            <c:if test="${allOrders.orderStatus.equals('ordered')}">
                            <tr>
                                <td>${allOrders.orderID}</td>
                                <td>
                                    <c:forEach var="allUsers" items="${sessionScope.allUsers}">
                                        <c:if test="${allOrders.userID == allUsers.userID }">
                                           <p>
                                            ${allUsers.name}<br/>
                                            ${allUsers.address}<br/>
                                            ${allUsers.email}<br/>
                                            ${allUsers.phoneNumber}
                                           </p>
                                        </c:if>
                                    </c:forEach>

                                </td>
                                <td>${allOrders.price} kr</td>

                                <td>Mangler godkendelse</td>

                                <td>
                                    <button formaction="admingenerate3d" class="btn btn-outline-dark"
                                            name="generate3d"> lav 3d model</button>


                                </td>

                            </tr>
                            </c:if>
                        </c:forEach>

                        <c:forEach var="allOrders" items="${sessionScope.allOrders}"
                                   varStatus="status">
                            <c:if test="${allOrders.orderStatus.equals('confirmed')}">
                                <tr>
                                    <td>${allOrders.orderID}</td>
                                    <td>
                                        <c:forEach var="allUsers" items="${sessionScope.allUsers}">
                                            <c:if test="${allOrders.userID == allUsers.userID }">
                                                <p>
                                                        ${allUsers.name}<br/>
                                                        ${allUsers.address}<br/>
                                                        ${allUsers.email}<br/>
                                                        ${allUsers.phoneNumber}
                                                </p>
                                            </c:if>
                                        </c:forEach>

                                    </td>
                                    <td>${allOrders.price} kr</td>

                                    <td>Godkendt</td>
                                    <td>
                                        <button formaction="admingenerate3d" class="btn btn-outline-dark"
                                                name="generate3d"> lav 3d model</button>


                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>

                        <c:forEach var="allOrders" items="${sessionScope.allOrders}"
                                   varStatus="status">
                            <c:if test="${allOrders.orderStatus.equals('paid')}">
                                <tr>
                                    <td>${allOrders.orderID}</td>
                                    <td>
                                        <c:forEach var="allUsers" items="${sessionScope.allUsers}">
                                            <c:if test="${allOrders.userID == allUsers.userID }">
                                                <p>
                                                        ${allUsers.name}<br/>
                                                        ${allUsers.address}<br/>
                                                        ${allUsers.email}<br/>
                                                        ${allUsers.phoneNumber}
                                                </p>
                                            </c:if>
                                        </c:forEach>

                                    </td>
                                    <td>${allOrders.price} kr</td>

                                    <td>Betalt</td>

                                    <td>
                                        <button formaction="admingenerate3d" class="btn btn-outline-dark"
                                                name="generate3d"> lav 3d model</button>


                                    </td>

                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>


                </div>
            </form>


        </div>

    </jsp:body>

</t:pagetemplate>
