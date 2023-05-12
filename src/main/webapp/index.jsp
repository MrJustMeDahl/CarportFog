<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage
    </jsp:attribute>

    <jsp:body>

        <div class="container">
            <div class="text-center">
                <img src="${pageContext.request.contextPath}/images/Forside.png" width="70%" class="img-fluid"/>
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
                    <p style="font-size: 120%">Lorem Ipsum is simply dummy text of the printing and typesetting
                        industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                        when an unknown printer took a galley of type and scrambled it to make a type specimen
                        book. It has survived not only five centuries, but also the leap into electronic
                        typesetting, remaining essentially unchanged. It was popularised in the 1960s with the
                        release of Letraset sheets containing Lorem Ipsum passages, and more recently with
                        desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
                </div>
            </div>
        </div>

    </jsp:body>

</t:pagetemplate>