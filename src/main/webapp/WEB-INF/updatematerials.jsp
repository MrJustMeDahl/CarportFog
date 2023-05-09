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
        <div class="card">
            <h2 class="text-center pt-5"> Materiale opatering </h2>

            <div class="card-body">
                <div class="container">
                    <tabl class="table align-center mt-5 table-responsive table-responsive-sm">
                        <thead>
                        <tr>
                            <p> Ændre Materiale </p>
                        </tr>
                        </thead>
                        <tr>
                            <form action="list" method="post">

                                <td>
                                    <select name="materialtype" id="materialID">
                                        <c:forEach items="${sessionScope.materialID}" var="material">
                                            <option value="${material.materialID}"> ${material.name} -${material.price}kr/mtr
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                    <%--
                                    <td>
                                        <label class="description-from-type-label" for="materialID"> kr/meter</label>
                                        <p>  indkøbspris </p>
                                    </td>
                                    --%>
                                <td>
                                    <button>
                                        formaction="changeprice" class="btn btn-outline-dark"
                                        name="newmaterialprice"> ændre pris
                                    </button>
                                </td>
                            </form>
                        </tr>
                        <tr>
                            <p>Tilføj nyt materiale</p>
                        </tr>
                        <tr>
                            <form action="list" method="post">
                                <td>

                                </td>
                            </form>
                        </tr>


                    </tabl>
                </div>
            </div>
        </div>


    </jsp:body>


</t:pagetemplate>