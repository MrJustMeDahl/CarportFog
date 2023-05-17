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

                <table class="table align-center mt-5 table-responsive table-responsive-sm">

                    <form action="list" method="post">
                    <th>
                    <h3> Ændre materiale </h3>
                    </th>



                            <%--
                            ****** Choose a functiontypegroup for the admin to update ******
                                    ****** With the last chosen type saved in the request scope ******
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
                        <tr>

                                <%--
                                ****** Choose the material the admin can update ******
                                   ****** With the last chosen type stored in the request scope ******
                                --%>

                            <c:choose>
                                <c:when test="${requestScope.materialfunction==null}">

                                    <div class="input-group">
                                        <td>
                                            <select name="materialdescription" id="editmaterialdescriptions"
                                                    class="input-group table align-center mt-2 table-responsive table-responsive-sm"
                                                    disabled>
                                                <option value="-1"> Vælg kategori ovenfor</option>
                                            </select>
                                        </td>
                                    </div>

                                </c:when>

                                <c:otherwise>

                                    <div class="input-group">
                                        <td>
                                            <select name="materialdescription" id="editmaterialdescription"
                                                    class="input-group table align-center mt-2 table-responsive table-responsive-sm">
                                                <c:if test="${requestScope.chosenmaterialId != -1}">
                                                    <option value="${requestScope.chosenMaterial.materialVariantID}"> ${requestScope.chosenMaterial.description}
                                                        - ${requestScope.chosenMaterial.type}
                                                        - ${requestScope.chosenMaterial.length} cm
                                                    </option>
                                                </c:if>
                                                <c:forEach items="${sessionScope.editmateriallist}"
                                                           var="editmateriallist">
                                                    <option value="${editmateriallist.materialVariantID}"> ${editmateriallist.description}
                                                        - ${editmateriallist.type}
                                                        - ${editmateriallist.length} cm
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>


                                    </div>

                                </c:otherwise>

                            </c:choose>

                        </tr>
                            <%--
                               ****** the materialdescription for the chosen material ******
                               --%>
                        <tr>
                            <td>
                                <div class="input-group mb-3">
                                    <input class="form-control table-responsive table-responsive-sm"
                                           id="changematerialdescription" class="d-inline form-control 2-10" type="text"
                                           name="changematerialdescription"
                                           value="${requestScope.chosenMaterial.description}">
                                    <span class="input-group-text" id="basis-materialdescription"> beskrivelse</span>
                                </div>
                            </td>
                        </tr>

                            <%--
                            ****** change the type for the chosen material with a dropdown menu ******
                            --%>
                        <tr>
                            <td>
                                <select name="changematerialtype" id="type"
                                        class="table align-center mt-2 table-responsive table-responsive-sm" disabled>
                                    <option value="${requestScope.chosenMaterial.function}"> ${requestScope.chosenMaterial.function}
                                    </option>
                                </select>
                            </td>
                        </tr>

                            <%--
                            ****** change the indicative price for the chosen material ******
                            --%>
                        <tr>
                            <td>
                                <div class="input-group mb-3">
                                    <input class="form-control table-responsive table-responsive-sm" id="average"
                                           class="d-inline form-control 2-10" type="number"
                                           name="changeprice"
                                           step="0.1" min="0.0" value="${requestScope.chosenMaterial.price}">
                                    <span class="input-group-text" id="updateprice"> kr/mtr</span>
                                </div>
                            </td>


                                <%--
                                ****** Button to forward the request to change the chosen material in the DB ******
                                --%>

                            <td>
                                <button formaction="editmaterial" class="btn btn-dark float-end"
                                        name="changematerial" value="${requestScope.chosenMaterial.materialID}">
                                    opdater
                                </button>

                            </td>
                        </tr>

                    </form>
                    <br/><br/>
                </table>

                <table class="table align-center mt-5 table-responsive table-responsive-sm">
                    <form action="list" method="post">


                            <%--
                            ****** Add a new material to the DB ******
                            --%>

                        <th>
                            <h3>Tilføj nyt materiale</h3>
                        </th>

                            <%--
                            ****** add a description to the material ******
                            --%>
                        <tr>
                            <form action="list" method="post">
                                <td>
                                    <input id="descriptiontext" class="d-inline form-control w-5" type="text"
                                           name="newmaterialdescription"
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
                        <tr>

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
                                    <input class="form-control" id="price" class="d-inline form-control 2-10"
                                           type="number"
                                           name="newmaterialprice"
                                           step="0.1" min="0.0">
                                    <span class="input-group-text"> kr/mtr</span>
                                </div>
                            </td>
                        </tr>
                            <%--
                            ******Add a length to the new material******
                            --%>
                        <tr>
                            <td>
                                <div class="input-group mb-3">
                                    <input class="form-control" id="length" class="d-inline form-control 2-10"
                                           type="number"
                                           name="newmateriallength"
                                           step="30" min="270" max="600">
                                    <span class="input-group-text"> cm</span>
                                </div>
                            </td>
                            <td>
                                <button formaction="addnewitem" class="btn btn-dark float-end"
                                        name="itemId">Tilføj
                                </button>
                                <br/>
                                <br/>

                                <c:if test="${requestScope.materialCreatedMessage != null}">
                                    <p> ${requestScope.materialCreatedMessage}</p></c:if>


                            </td>
                        </tr>

                    </form>

                </table>
            </div>
        </div>


    </jsp:body>


</t:pagetemplate>