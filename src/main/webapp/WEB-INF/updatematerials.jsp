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
                <div class="container">
                    <tabl class="table align-center mt-5 table-responsive table-responsive-sm">
                        <thead>
                        <tr>
                            <h3> Ændre materiale </h3>
                        </tr>
                        </thead>

                            <%--
                            ****** Choose a categorygroup for the admin to update ******
                            --%>
                        <form action="list" method="post">
<%--
                            <tr>
                                <select name="materialcategory" id="category"
                                        class="table align-center mt-2 table-responsive table-responsive-sm">
                                    <c:forEach items="${applicationScope.allCategories}" var="categorymaterialID">
                                        <option value="${applicationScope.allCategories.categoryID}"> ${applicationScope.allCategories.name}
                                        </option>
                                    </c:forEach>

                                </select>
                            </tr>
--%>
                        <%--
                        ****** Choose a functiontypegroup for the admin to update ******
                        --%>
                            <tr>
                                <select name="materialfunction" id="chosentype"
                                        class="table align-center mt-2 table-responsive table-responsive-sm">
                                        <option value="1"> ${applicationScope.allPosts.get(0).function}
                                        </option>
                                        <option value="2"> ${applicationScope.allRafters.get(0).function}
                                        </option>
                                        <option value="3"> ${applicationScope.allPurlins.get(0).function}
                                        </option>
                                </select>
                            </tr>

                        <%--
                        ****** the materialID for the chosen material, can't be changed ******
                        --%>
                            <tr>
                                <div class="input-group mb-3">
                                    <input class="form-control table-responsive table-responsive-sm" id="materialID" class="d-inline form-control 2-10" type="text"
                                           name="materialID">
                                        <span class="input-group-text" id="basic-materialID"> materiale id</span>
                                </div>
                            </tr>

                        <%--
                        ****** the description for the chosen material ******
                        --%>
                            <tr>
                                <div class="input-group mb-3">
                                    <input class="form-control table-responsive table-responsive-sm" id="description" class="d-inline form-control 2-10" type="text"
                                           name="description">
                                        <span class="input-group-text" id="updatedescription"> beskrivelse</span>
                                </div>
                            </tr>

                        <%--
                        ****** change the type for the chosen material with a dropdown menu ******
                        --%>
                            <tr>
                                <select name="materialtype" id="type"
                                        class="table align-center mt-2 table-responsive table-responsive-sm">
                                    <option value="1"> ${applicationScope.allPosts.get(0).function}
                                    </option>
                                    <option value="2"> ${applicationScope.allRafters.get(0).function}
                                    </option>
                                    <option value="3"> ${applicationScope.allPurlins.get(0).function}
                                    </option>
                                </select>
                            </tr>

                        <%--
                        ****** change the function in a dropdown for the chosen material ******
                        --%>
                            <tr>
                                <select name="materialfunction" id="function"
                                        class="table align-center mt-2 table-responsive table-responsive-sm">
                                    <option value="1"> ${applicationScope.allPosts.get(0).function}
                                    </option>
                                    <option value="2"> ${applicationScope.allRafters.get(0).function}
                                    </option>
                                    <option value="3"> ${applicationScope.allPurlins.get(0).function}
                                    </option>
                                </select>
                            </tr>

                        <%--
                        ****** change the indicative price for the chosen material ******
                        --%>
                            <tr>
                                <div class="input-group mb-3">
                                    <input class="form-control table-responsive table-responsive-sm" id="average" class="d-inline form-control 2-10" type="number"
                                           name="price"
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
                                        <option value="1"> ${applicationScope.allPosts.get(0).type}
                                        </option>
                                        <option value="2"> ${applicationScope.allPosts.get(1).type}
                                        </option>
                                        <option value="3"> ${applicationScope.allPosts.get(2).type}
                                        </option>
                                    </select>
                                </td>
                            </div>
                        </tr>

                        <%--
                        ****** add a new type in the DB ******
                        --%>
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

                        <%--
                        ****** choose a function for the material in the dropdown ******
                        --%>
                        <tr>
                            <td>
                                <select name="newmaterialfunction" id="newmaterialfunction"
                                        class="table align-center mt-2 table-responsive table-responsive-sm">
                                    <option value="1"> ${applicationScope.allPosts.get(0).function}
                                    </option>
                                    <option value="2"> ${applicationScope.allRafters.get(0).function}
                                    </option>
                                    <option value="3"> ${applicationScope.allPurlins.get(0).function}
                                    </option>
                                </select>
                            </td>

                        </tr>

                        <%--
                        ****** add a new function in the DB ******
                        --%>
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
                        </td>
                        </form>
                        </tr>
                    </tabl>
                </div>
            </div>


        </div>


    </jsp:body>


</t:pagetemplate>