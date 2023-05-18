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
        <h1>Design din egen carport!</h1>


        <h5>Carport</h5>
        <form action="carport" method="post">
            længde: <select name="length" >
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
            <br/>
            bredde: <select name="width">
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
                <option value="550">550 cm</option>
                <option value="580">580 cm</option>
                <option value="600">600 cm</option>
            </select>
            <br/>
            højde: <select name="height">
                <option value="180">180 cm</option>
                <option value="210">210 cm</option>
                <option value="240">240 cm</option>
                <option value="270">270 cm</option>
                <option value="300">300 cm</option>

            </select>
            <br/>
            <br/>
            <c:if test="${requestScope.shed == 1}">
                <h5>Skur</h5>
                Længde for skur: <select name="shedLength">
                <option value="100">180 cm</option>
                <option value="120">210 cm</option>
                <option value="140">240 cm</option>
                <option value="160">270 cm</option>
                <option value="180">300 cm</option>
            </select>
                <br/>
                Bredde for skur: <select name="shedWidth">
                <option value="100">180 cm</option>
                <option value="120">210 cm</option>
                <option value="140">240 cm</option>
                <option value="160">270 cm</option>
                <option value="180">300 cm</option>
            </select>
                <br/>

                <br/>
            </c:if>


            <input type="submit" value="Tilføj til kurv">


        </form>




    </jsp:body>
</t:pagetemplate>