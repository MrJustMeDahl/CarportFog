<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
            Opret konto
    </jsp:attribute>

    <jsp:body>

        <div class="align-center text-center">
            <div class="card w-50 border-primary mx-auto">
                <div class="card-body">
                    <div class="row">
                        <h1 class="card-title">Opret ny konto</h1>
                    </div>
                    <form action="signup" method="post">
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="email" name="email" placeholder="email@email.dk">
                        </div>
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="password" name="password" placeholder="Kodeord">
                        </div>
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="text" name="fullName" pattern="[a-zA-Z]*" placeholder="Fulde navn">
                        </div>
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="number" min="10000000" max="99999999" name="phoneNumber"
                                   placeholder="Telefon nr.">
                        </div>
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="text" name="address" placeholder="Adresse">
                        </div>
                        <div class="row mt-3 w-50 mx-auto">
                            <button class="btn btn-primary" type="submit" value="Opret">
                                Opret konto
                            </button>
                        </div>
                        <c:if test="${requestScope.registermessage != null}">
                            <div class="row mt-3 w-50 mx-auto">
                                <p>${requestScope.registermessage}</p>
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:pagetemplate>