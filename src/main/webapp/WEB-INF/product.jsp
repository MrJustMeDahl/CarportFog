<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Produkt katalog
    </jsp:attribute>

    <jsp:body>
        <h1><u>Produkt katalog</u></h1>
        <div class="row">
            <div class="col-md-6  d-block">
                <form id="formNoShed" action="carport" method="get">
                    <div class="card" onclick="submitFormNoShed()">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/images/carportFladt.jpg">
                        <div class="card-body">
                            <h3 class="card-title">Carport med fladt tag</h3>
                            <input type="hidden" name="shed" value="0">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-6  d-block">
                <form id="formShed" action="carport" method="get">
                    <div class="card" onclick="submitFormShed()">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/images/carportmedskur.jpg">
                        <div class="card-body">
                            <h3 class="card-title">Carport med fladt tag inkl. skur</h3>
                            <input type="hidden" name="shed" value="1">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:pagetemplate>

<script>
    function submitFormNoShed() {
        document.getElementById("formNoShed").submit();
    }

    function submitFormShed() {
        document.getElementById("formShed").submit();
    }
</script>