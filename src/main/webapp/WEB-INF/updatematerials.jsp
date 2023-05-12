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

                <tabl class="table align-center mt-5 table-responsive table-responsive-sm">
                    <thead>
                    <tr>
                        <h3> Ændre materiale </h3>
                    </tr>
                    </thead>

                    <form action="list" method="post">

                            <%--
                            ****** Choose a functiontypegroup for the admin to update ******
                            --%>
                        <div class="input-group">

                            <tr>
                                <td>
                                    <select name="materialfunction" id="chosentype"
                                            class="table align-center mt-2 table-responsive table-responsive-sm">
                                        <c:if test="${requestScope.materialfunction!=null}">
                                            <option value="${requestScope.materialfunction}"> ${applicationScope.allMaterialFunctions.get(requestScope.materialfunction - 1)}
                                            </option>
                                        </c:if>
                                        <option value="1"> ${applicationScope.allMaterialFunctions.get(0)}
                                        </option>
                                        <option value="2"> ${applicationScope.allMaterialFunctions.get(1)}
                                        </option>
                                        <option value="3"> ${applicationScope.allMaterialFunctions.get(2)}
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <button formaction="editchosenmaterialfunction"
                                            class="btn btn-outline-dark float-end"
                                            name="Vælg funktion">opdater
                                    </button>
                                </td>
                            </tr>
                        </div>
                        <c:choose>
                            <c:when test="${requestScope.materialfunction==null}">
                                <tr>
                                    <div class="input-group">

                                        <select name="materialdescription" id="editmaterialdescriptions"
                                                class="input-group table align-center mt-2 table-responsive table-responsive-sm"
                                                disabled>
                                            <option value="-1"> Vælg kategori ovenfor</option>
                                        </select>
                                        <td>
                                            <button formaction="deletechosenmaterial"
                                                    class="btn btn-outline-danger mb-3 float-right"
                                                    name="deletematerial"
                                                    type="button"
                                                    id="button-addons">Slet
                                            </button>
                                        </td>
                                    </div>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <div class="input-group">

                                        <select name="materialdescription" id="editmaterialdescription"
                                                class="input-group table align-center mt-2 table-responsive table-responsive-sm">
                                            <c:if test="${requestScope.chosenmaterialId != -1}">
                                                <option value="${requestScope.chosenmaterialId}"> ${requestScope.editmateriallist.get(requestScope.chosenmaterialId - 1).description}
                                                </option>
                                            </c:if>
                                            <c:forEach items="${requestScope.editmateriallist}"
                                                       var="editmateriallist">
                                                <option value="${editmateriallist.materialID}"> ${editmateriallist.description}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <td>
                                            <button formaction="deletechosenmaterial"
                                                    class="btn btn-outline-danger mb-3 float-right"
                                                    name="deletematerial"
                                                    type="button"
                                                    id="button-addon">Slet
                                            </button>
                                        </td>
                                    </div>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                            <%--
                            ****** the materialID for the chosen material, can't be changed ******
                            --%>
                        <tr>
                            <input class="input-group mb-3">
                            <input class="form-control table-responsive table-responsive-sm"
                                   id="changematerialdescription" class="d-inline form-control 2-10" type="text"
                                   name="changematerialdescription">${requestScope.editmateriallist.get(requestScope.chosenmaterialId).description}</input>
                            <span class="input-group-text" id="basis-materialdescription"> beskrivelse</span>
                        </tr>

                            <%--
                            ****** change the type for the chosen material with a dropdown menu ******
                            --%>
                        <tr>
                            <select name="changematerialtype" id="type"
                                    class="table align-center mt-2 table-responsive table-responsive-sm">
                                <option value="1"> ${applicationScope.allMaterialFunctions.get(0)}
                                </option>
                                <option value="2"> ${applicationScope.allMaterialFunctions.get(1)}
                                </option>
                                <option value="3"> ${applicationScope.allMaterialFunctions.get(2)}
                                </option>
                            </select>
                        </tr>

                            <%--
                            ****** change the indicative price for the chosen material ******
                            --%>
                        <tr>
                            <div class="input-group mb-3">
                                <input class="form-control table-responsive table-responsive-sm" id="average"
                                       class="d-inline form-control 2-10" type="number"
                                       name="changeprice"
                                       step="0.1" min="0.0">
                                <span class="input-group-text" id="updateprice"> kr/mtr</span>
                            </div>
                        </tr>

                            <%--
                            ****** Button to forward the request to change the chosen material in the DB ******
                            --%>
                        <td>
                            <button formaction="editmaterial" class="btn btn-dark float-end"
                                    name="changematerial">
                                opdater
                            </button>

                        </td>
                    </form>
                    <br/><br/>

                        <%--
                        ****** Add a new material to the DB ******
                        --%>

                    <tr>
                        <h3>Tilføj nyt materiale</h3>
                    </tr>

                        <%--
                        ****** add a description to the material ******
                        --%>
                    <tr>
                        <form action="list" method="post">
                            <td>
                                <input id="descriptiontext" class="d-inline form-control w-5" type="text"
                                       name="description"
                                       placeholder="Materiale Beskrivelse">
                            </td>

                                <%--
                                ****** choose a type for the material in the dropdown ******
                                --%>
                    <tr>
                        <div class="input-group">
                            <td>

                                <select name="newmaterialtype" id="newmaterialtype"
                                        class="table align-center mt-2 table-responsive table-responsive-sm">
                                    <option value="1"> ${applicationScope.allMaterialTypes.get(0)}
                                    </option>
                                    <option value="2"> ${applicationScope.allMaterialTypes.get(1)}
                                    </option>
                                    <option value="3"> ${applicationScope.allMaterialTypes.get(2)}
                                    </option>
                                </select>
                            </td>
                        </div>
                    </tr>

                        <%--
                        ****** add a new type in the DB ******

                        <div class="input-group">
                        <td>
                            <input class="form-control" id="typetext" class="d-inline form-control w-5" type="text"
                                   name="type"
                                   placeholder="New Material Type">
                        </td>
                            <td>
                                <button formaction="addnewfunction" class="btn btn-outline-dark float-end"
                                        name="function">Tilføj
                                </button>
                            </td>
                        </div>
                        --%>

                        <%--
                        ****** choose a function for the material in the dropdown ******
                        --%>
                    <tr>
                        <td>
                            <select name="newmaterialfunction" id="newmaterialfunction"
                                    class="table align-center mt-2 table-responsive table-responsive-sm">
                                <option value="1"> ${applicationScope.allMaterialFunctions.get(0)}
                                </option>
                                <option value="2"> ${applicationScope.allMaterialFunctions.get(1)}
                                </option>
                                <option value="3"> ${applicationScope.allMaterialFunctions.get(2)}
                                </option>
                            </select>
                        </td>

                    </tr>

                        <%--
                        ****** add a new function in the DB ******

                        <div class="input-group">
                        <td>
                            <input id="functiontext" class="d-inline form-control w-5" type="text"
                                   name="function"
                                   placeholder="New Material Function">
                        </td>
                            <td>
                                <button formaction="addnewfunction" class="btn btn-outline-dark float-end"
                                        name="typeid">Tilføj
                                </button>
                            </td>
                        </div>
                        --%>

                        <%--
                        ****** set the indicative price for the item  ******
                        --%>

                    <td>
                        <div class="input-group mb-3">
                            <input class="form-control" id="price" class="d-inline form-control 2-10" type="number"
                                   name="price"
                                   step="0.1" min="0.0">
                            <span class="input-group-text"> kr/mtr</span>
                        </div>
                    </td>
                    <td>
                        <button formaction="addnewitem" class="btn btn-dark float-end"
                                name="itemId">Tilføj
                        </button>
                        <br/>
                        <br/>
                        <c:if test="${requestScope.allFormsarefilled}">
                            <p> Du har tilføjet et nyt materiale </p>
                        </c:if>

                        <c:if test="${requestscope.notAllFormsfilled}">

                            <p> alle felter skal være udfyldt for at tilføje et nyt produkt. </p>

                        </c:if>


                    </td>
                    </form>
                    </tr>
                </tabl>
            </div>
        </div>


        </div>


    </jsp:body>


</t:pagetemplate>