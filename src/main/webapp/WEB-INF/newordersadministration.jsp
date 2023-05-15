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
                    <table class="table-striped">
                        <thead>
                        <tr class="table-info">
                            <th>Ordre nr:</th>
                            <th>Kunde mail:</th>
                            <th>Kunde tlf:</th>
                            <th></th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>

    </jsp:body>
</t:pagetemplate>