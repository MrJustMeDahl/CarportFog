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
        <h1>Ordrer</h1>

        <br/>
        <br/>
        <table class="table table-striped">
            <thead>
            <th>Ordre ID:</th>
            <th>Ordre Status:</th>
            <th>Samlet pris:</th>
            <th>Vejledende pris:</th>
            <th>Carport Højde:</th>
            <th>Carport Bredde:</th>
            <th>Carport Længde:</th>
            <th></th>
            </thead>
            <form>
                <c:forEach var="order" items="${requestScope.orderlist}">
                        <tr>
                            <td>
                                    ${order.orderID}
                            </td>

                            <td>
                                    ${order.orderStatus}
                            </td>

                            <td>
                                    ${order.price}
                            </td>

                            <td>
                                    ${order.indicativePrice}
                            </td>

                            <td>
                                    ${order.carport.minHeight}
                            </td>

                            <td>
                                    ${order.carport.width}
                            </td>

                            <td>
                                    ${order.carport.length}
                            </td>


                            <c:if test="${order.orderStatus == 'confirmed'}">
                                <td>
                                    <button type="submit" name="currentID" value="${order.orderID}"
                                            class="btn btn-sm - btn-outline-success" formaction="orderandpayment"
                                            formmethod="get">Betal
                                    </button>
                                </td>
                            </c:if>
                        </tr>

                        <tr></tr>


                </c:forEach>
            </form>
        </table>

        </table>


    </jsp:body>
</t:pagetemplate>