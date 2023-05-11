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

        Material material = null;

        String description = request.getParameter("description");
        String materialType = request.getParameter("type");
        String materialFunction = request.getParameter("function");
        int price = Integer.parseInt(request.getParameter("price"));

        try {

            MaterialFacade.newMaterial(description, materialType, materialFunction, price, connectionPool);

        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
