package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "carport", urlPatterns = {"/carport"} )
public class Carports extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    /**
     * doGet is used to redirect to the carport.jsp, where the user is able to customize the dimensions of a carport.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        //response.sendRedirect("WEB-INF/carport.jsp");
        request.getRequestDispatcher("WEB-INF/carport.jsp").forward(request, response);

    }


    /**
     * doPost is designed to take the users input and make use of the createOrder() method. The order is saved in the DB and on
     * the request and session scope for later use, fx in the shoppingbasket.
     * HER SKAL LOADES MATERIALER IND!!!!!! MANGLER STADIG.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        Map<Material, Integer> materials = new HashMap<Material, Integer>();

        User user = (User) session.getAttribute("user");

        int width = Integer.parseInt(request.getParameter("width"));
        int length = Integer.parseInt(request.getParameter("length"));
        int  height = Integer.parseInt(request.getParameter("height"));

        Carport carport = new Carport(materials, 5000, 6000, width, length, height);


        try{
            Order order = OrderFacade.createOrder(carport, user.getUserID(), carport.getPrice(), carport.getIndicativePrice(), connectionPool);
            OrderFacade.addItemlistToDB(materials, order.getOrderID(), connectionPool);
            request.getRequestDispatcher("shoppingbasket").forward(request, response);
        }
        catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

}