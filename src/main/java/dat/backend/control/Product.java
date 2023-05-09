package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "product", urlPatterns = {"/product"} )
public class Product extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    /**
     * doGet will redirect the user to products.jsp, but later we will add more products, which then have to be loaded
     * through this servlet.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        request.getRequestDispatcher("WEB-INF/product.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {


    }

}