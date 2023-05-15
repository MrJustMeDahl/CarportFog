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

@WebServlet(name = "EditMaterial", value = "/editmaterial")
public class EditMaterial extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * The editmaterialServlet is used to edit items already in the database.
     * only the admin user can use this servlet, and can change the price and description of a material
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
        int materialId = Integer.parseInt(request.getParameter("materialdescription"));
        double newPrice = Double.parseDouble(request.getParameter("changeprice"));
        String newDescription = request.getParameter("changematerialdescription");

        try {
            MaterialFacade.updateMaterial(materialId, newPrice, newDescription, connectionPool);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        //TODO Her skal det nye applicationScope laves med updaterede ændringer



        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request,response);
    }
}
