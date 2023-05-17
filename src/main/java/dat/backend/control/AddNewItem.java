package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddNewItem", value = "/addnewitem")
public class AddNewItem extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * The AddNewItemServlet is used to create a new item in the database.
     * only the admin user can use this servlet, and is needed to fill 5 criteria before he/she is able to add items to the database
     * <p>
     * first it takes the input, one by one and increases a counter, if the counter reaches 5, then there is a check in the DB for the same item, if none is found, then a new  materialtype is created
     * thereafter it checks the DB for the item, if none is found then the item is created. lastly it adds the new material to the MaterialList on the application scope.
     * lastly is resets all the the request scopes used on the jsp page.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @author CarstenJuhl
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        ServletContext application = getServletContext();
        List<Material> materials = null;
        String description = null;
        int materialType = -1;
        int materialFunction = -1;
        double price = -1;
        int length = -1;
        int formsfilled = 0;

        try {
            description = request.getParameter("newmaterialdescription");
            formsfilled++;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try {
            materialType = Integer.parseInt(request.getParameter("newmaterialtype"));
            formsfilled++;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try {
            materialFunction = Integer.parseInt(request.getParameter("newmaterialfunction"));
            formsfilled++;
            System.out.println("function added" + materialFunction);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try {
            price = Double.parseDouble(request.getParameter("newmaterialprice"));
            formsfilled++;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try {
            length = Integer.parseInt(request.getParameter("newmateriallength"));
            formsfilled++;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        Material newMaterial = null;

        switch (materialFunction) {
            case 1:
                materials = (List<Material>) application.getAttribute("allPosts");

                break;

            case 2:
                materials = (List<Material>) application.getAttribute("allPurlins");

                break;

            case 3:
                materials = (List<Material>) application.getAttribute("allRafters");

                break;

            default:
                throw new IllegalArgumentException();
        }


        if (formsfilled == 5) {
            try {
                int materialId = -1;
                int newMaterialVariantId = -1;
                boolean materialExists = false;
                boolean materiallengthExists = false;
                for (Material m : materials) {
                    if (m.getDescription().equals(description)) {
                        materialId = m.getMaterialID();
                        materialExists = true;
                    }
                }
                if (!materialExists) {
                    materialId = MaterialFacade.newMaterial(description, materialType, materialFunction, price, connectionPool);
                }
                for (Material m : materials) {
                    if (m.getMaterialID() == materialId) {
                        if (m.getLength() == length) {
                            materiallengthExists = true;
                        }
                    }
                }
                if (!materiallengthExists) {
                    newMaterialVariantId = MaterialFacade.addLength(materialId, length, connectionPool);
                    request.setAttribute("materialCreatedMessage", "new Material added");
                } else {
                    request.setAttribute("materialCreatedMessage", "Material Already Exists");
                }
                String type = "";

                switch (materialType) {
                    case 1:
                        type = "træ";
                        break;

                    case 2:
                        type = "metal";
                        break;

                    case 3:
                        type = "plastik";
                        break;

                }

                switch (materialFunction) {
                    case 1:
                        newMaterial = new Post(materialId, newMaterialVariantId, description, type, "stolpe", price, length);

                        break;

                    case 2:
                        newMaterial = new Purlin(materialId, newMaterialVariantId, description, type, "rem", price, length);

                        break;

                    case 3:
                        newMaterial = new Rafter(materialId, newMaterialVariantId, description, type, "spær", price, length);

                        break;

                    default:
                        throw new IllegalArgumentException();
                }

                materials.add(newMaterial);


            } catch (DatabaseException e) {
                request.setAttribute("errormessage", e);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }


        } else {
            request.setAttribute("materialCreatedMessage", "The material failed to be created");
        }
        request.setAttribute("materialfunction", null);
        request.setAttribute("chosenmaterialId", -1);
        request.setAttribute("chosenMaterial", null);
        session.setAttribute("editmateriallist", null);

        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request, response);


    }
}
