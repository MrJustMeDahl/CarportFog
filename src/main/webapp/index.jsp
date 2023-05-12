<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Velkommen hos Fog Carporte
    </jsp:attribute>

    <jsp:body>

        <div class="container">
            <div class="text-center">
                <img src="${pageContext.request.contextPath}/images/forside3.png" width="90%" class="img-fluid"/>
                <h1 class="h1" style="font-size: 700%">
                    Fog Carporte
                </h1>
            </div>
        </div>
        <div class="container mt-5">
            <div class="row text-center">
                <div class="col-4">
                    <img src="${pageContext.request.contextPath}/images/forside2.png" width="80%"
                         class="img-fluid"/>
                </div>
                <div class="col-8">
                    <p style="font-size: 120%">Hos Fog benytter vi udelukkende bæredygtigt træ, som er certificeret af
                        FSC (Forest Stewardship Council) eller PEFC (Programme for the Endorsement of Forest
                        Certification).
                        Ved at vælge bæredygtigt træ kan vi bidrage til at bevare vores skove,
                        mindske negativ indvirkning på miljøet og støtte lokale samfund, der er afhængige af
                        skovressourcer. Det er vigtigt at være opmærksom på, hvor vores træprodukter kommer fra, og
                        bestræbe os på at vælge produkter, der er produceret ansvarligt og bæredygtigt.
                    </p>
                </div>
            </div>
        </div>

    </jsp:body>

</t:pagetemplate>