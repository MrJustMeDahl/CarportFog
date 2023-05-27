<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
            Log ind
    </jsp:attribute>

    <jsp:body>

        <div class="align-center text-center">
            <div class="card w-50 border-primary mx-auto">
                <div class="card-body">
                    <div class="row">
                        <h1>Log ind:</h1>
                    </div>
                    <form>
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="email" name="email" placeholder="email@email.dk">
                        </div>
                        <div class="row mt-3 w-75 mx-auto">
                            <input type="password" name="password" placeholder="Kodeord">
                        </div>
                        <div class="row mt-3 w-25 mx-auto">
                            <button class="btn btn-primary" type="submit" formmethod="post" formaction="login">
                                Log ind
                            </button>
                        </div>
                        <div class="row mt-2 w-50 mx-auto">
                            <c:choose>
                                <c:when test="${requestScope.registermessage != null}">
                                    <p>${requestScope.registermessage}</p>
                                </c:when>
                                <c:otherwise>
                                    <p>Er du ikke allerede kunde hos os kan du tilmelde dig <a
                                            href="register.jsp">her.</a></p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </jsp:body>
</t:pagetemplate>