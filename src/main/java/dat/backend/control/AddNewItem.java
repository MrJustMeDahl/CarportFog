package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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
     * only the admin user can use this servlet, and is needed to fill 4 criteria before he/she is able to add items to the database
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
        User admin = (User) session.getAttribute("admin");
        String description = null;
        int materialType = -1;
        int materialFunction = -1;
        double price = -1;
        boolean allFormsAreFilled = false;
        boolean notAllFormsAreFilled = false;
        int formsfilled = 0;

        try {
            description = request.getParameter("newmaterialdescription");
            formsfilled++;
            System.out.println("description added" + description);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            System.out.println("Error adding description");
        }

        try {
            materialType = Integer.parseInt(request.getParameter("newmaterialtype"));
            formsfilled++;
            System.out.println("type added" + materialType);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            System.out.println("Error adding type");
        }

        try {
            materialFunction = Integer.parseInt(request.getParameter("newmaterialfunction"));
            formsfilled++;
            System.out.println("function added" + materialFunction);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            System.out.println("Error adding function");
        }

        try {
            price = Double.parseDouble(request.getParameter("newmaterialprice"));
            formsfilled++;
            System.out.println("price added" + price);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            System.out.println("Error adding price");
        }

        if (formsfilled == 4) {
            allFormsAreFilled = true;
            System.out.println("Formsfilled = 4");
            try {
                MaterialFacade.newMaterial(description, materialType, materialFunction, price, connectionPool);
                request.setAttribute("allFormsAreFilled", allFormsAreFilled);
                request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request,response);
                System.out.println("New item added");

            } catch (DatabaseException e) {
                request.setAttribute("errormessage", e);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }else{
            notAllFormsAreFilled = true;
            request.setAttribute("notAllFormsAreFilled", notAllFormsAreFilled);

            request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request,response);
        }

    }
}
