<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Bestil din carport
    </jsp:attribute>

    <jsp:body>
        <h1>Design din egen carport!</h1>

        <div class="container text-center">
            <div class="row">
                <div class="col-md-6">
                    <div class="row">
                        <div class="card">
                            <c:choose>
                                <c:when test="${requestScope.shed == 1}">
                                    <img src="${pageContext.request.contextPath}/images/carportmedskur.jpg">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/carportFladt.jpg">
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row mt-1 p2">
                        <div class="card">
                            <p class="card-text">I Fog sikrer vi, at du får den rigtige start. Vi står klar med gode råd
                                om de forhold,
                                du
                                skal
                                være opmærksom på, når du bygger din egen carport.
                                <br/>
                                Vores carporte er designet til at passe lige præcis til din indkørsel,
                                bare vælg dine egne mål på carporten og vi vender hurtigst muligt tilbage med et tilbud
                                til
                                dig.
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="row mx-auto">
                            <h3>Carport</h3>
                        </div>
                        <form action="carport" method="post">
                            <div class="row mx-auto">
                                <h5 class="card-title mt-2">Længde:</h5>
                                <select name="length" style="width: 50%" class="mt-2 mx-auto">
                                    <option value="400">400 cm</option>
                                    <option value="430">430 cm</option>
                                    <option value="460">460 cm</option>
                                    <option value="490">490 cm</option>
                                    <option value="520">520 cm</option>
                                    <option value="550">550 cm</option>
                                    <option value="580">580 cm</option>
                                    <option value="610">610 cm</option>
                                    <option value="640">640 cm</option>
                                    <option value="670">670 cm</option>
                                    <option value="700">700 cm</option>
                                    <option value="730">730 cm</option>
                                    <option value="760">760 cm</option>
                                    <option value="780">780 cm</option>
                                </select>
                            </div>

                            <div class="row mx-auto">
                                <h5 class="card-title mt-2">Bredde:</h5>
                                <select name="width" style="width: 50%" class="mt-2 mx-auto">
                                    <option value="310">310 cm</option>
                                    <option value="340">340 cm</option>
                                    <option value="370">370 cm</option>
                                    <option value="400">400 cm</option>
                                    <option value="430">430 cm</option>
                                    <option value="460">460 cm</option>
                                    <option value="490">490 cm</option>
                                    <option value="520">520 cm</option>
                                    <option value="550">550 cm</option>
                                    <option value="580">580 cm</option>
                                    <option value="600">600 cm</option>
                                </select>
                            </div>

                            <div class="row mx-auto">
                                <h5 class="card-title mt-2">Højde:</h5>
                                <select name="height" style="width: 50%" class="mt-2 mx-auto">
                                    <option value="180">180 cm</option>
                                    <option value="210">210 cm</option>
                                    <option value="240">240 cm</option>
                                    <option value="270">270 cm</option>
                                    <option value="300">300 cm</option>

                                </select>
                            </div>


                            <c:if test="${requestScope.shed == 1}">
                                <div class="row mx-auto">
                                    <h3>Skur</h3>
                                </div>
                                <div class="row mx-auto">
                                    <h5 class="card-title mt-2">Længde for skur:</h5>
                                    <select name="shedLength" style="width: 50%" class="mt-2 mx-auto">
                                        <option value="100">100 cm</option>
                                        <option value="120">120 cm</option>
                                        <option value="140">140 cm</option>
                                        <option value="160">160 cm</option>
                                        <option value="180">180 cm</option>
                                        <option value="200">200 cm</option>
                                        <option value="220">220 cm</option>
                                        <option value="240">240 cm</option>
                                    </select>
                                </div>
                                <div class="row mx-auto">
                                    <h5 class="card-title mt-2">Bredde for skur:</h5>
                                    <select name="shedWidth" style="width: 50%" class="mt-2 mx-auto">
                                        <option value="100">100 cm</option>
                                        <option value="130">130 cm</option>
                                        <option value="160">160 cm</option>
                                        <option value="190">190 cm</option>
                                        <option value="220">220 cm</option>
                                        <option value="250">250 cm</option>
                                        <option value="280">280 cm</option>
                                        <option value="310">310 cm</option>
                                        <option value="340">340 cm</option>
                                        <option value="370">370 cm</option>
                                        <option value="400">400 cm</option>
                                        <option value="430">430 cm</option>
                                        <option value="460">460 cm</option>
                                        <option value="490">490 cm</option>
                                        <option value="520">520 cm</option>
                                    </select>
                                </div>
                            </c:if>
                            <input type="submit" value="Tilføj til kurv" class="mt-3 btn btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:pagetemplate>