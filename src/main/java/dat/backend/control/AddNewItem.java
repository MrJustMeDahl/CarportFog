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
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");
        String description = null;
        int materialType = -1;
        String materialFunction = null;
        float price = -1;
        boolean notAllFormsfilled = false;
        int formsfilled = 0;






        try{
            description = request.getParameter("description");
            formsfilled++;
        } catch(IllegalArgumentException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try{
            materialType = Integer.parseInt(request.getParameter("type"));
            formsfilled++;
        } catch (IllegalArgumentException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try{
            materialFunction = request.getParameter("function");
            formsfilled++;
        }catch (IllegalArgumentException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        try{
            price = Float.parseFloat(request.getParameter("price"));
            formsfilled++;
        }catch (IllegalArgumentException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }


        try {

            MaterialFacade.newMaterial(description, materialType, materialFunction, price, connectionPool);

        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        if(formsfilled <= 3){
            notAllFormsfilled = true;
        }

        request.setAttribute("notAllFormsfilled", notAllFormsfilled);
        request.getRequestDispatcher("WEB-INF/updatematerials.jsp");

    }
}
