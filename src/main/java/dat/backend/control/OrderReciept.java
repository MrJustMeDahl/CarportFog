package dat.backend.control;

import dat.backend.model.entities.ItemList;
import dat.backend.model.entities.Order;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "orderreciept", urlPatterns = {"/orderreciept"} )
public class OrderReciept extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    /**
     * doGet sends order to the request scope and redirects to reciept.jsp
     * @param request   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet
     *
     * @param response  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client
     *
     * @throws IOException
     * @throws ServletException
     * @author Danyal
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        Order order = (Order) request.getAttribute("order");
        request.setAttribute("order", order);

        request.getRequestDispatcher("WEB-INF/reciept.jsp").forward(request, response);



    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");


    }

}