package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Carport;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.User;
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
     * */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.sendRedirect("carport.jsp");
    }


    /**
     * doPost is designed to take the users input on a requestscope and save it for the shoppingbasket.
     * HER SKAL LOADES MATERIALER IND!!!!!! MANGLER STADIG
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        Map<Material, Integer> materials = null;
        User user = (User) session.getAttribute("user");

        int width = Integer.parseInt(request.getParameter("width"));
        int length = Integer.parseInt(request.getParameter("length"));
        int  height = Integer.parseInt(request.getParameter("height"));

        Carport carport = new Carport(materials, 5000, 6000, width, length, height);




        try{
            OrderFacade.createOrder(carport, user.getUserID(), carport.getPrice(), carport.getIndicativePrice(), connectionPool);
            request.getRequestDispatcher("shoppingbasket").forward(request, response);
        }
        catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

}